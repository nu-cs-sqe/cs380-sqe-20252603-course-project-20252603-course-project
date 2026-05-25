# BVA Analysis for `LocaleLoader`

`LocaleLoader` is a core utility class responsible for reading application-wide supported locale metadata from an external configuration file (`supported-locales.properties`). It exposes the static method `getSupportedLocales()`, which dynamically parses IETF BCP 47 language tags and maps them to their respective display-name resource bundle keys.

The implementation caches this mapping inside an immutable `Map` to guarantee thread safety and decouple configuration from Java source code changes.

The Boundary Value Analysis (BVA) below targets the properties parsing, data integrity, and error-handling contract of the loader.

---

## Step 1-3 Summary

- Step 1, input equivalence classes:
    - `supported-locales.properties` file state accepts:
        - Completely empty file.
        - Exactly two valid locale entries (minimum baseline requirement).
        - More than two valid locale entries (scalability verification).
        - Well-formed BCP 47 tags (e.g., `en-US`, `es-US`).
        - Structural anomalies (e.g., missing property keys, empty values, spaces, or malformed tags like `---`).
        - Missing file entirely from the classpath.
    - Class initialization behavior depends on:
        - Successful IO stream location via ClassLoader.
        - Successful execution of `Properties.load()`.
        - Proper handling of empty or corrupted property layouts.

- Step 1, output equivalence classes:
    - `getSupportedLocales()` returns an immutable, non-null, ordered `Map<Locale, String>`.
    - Map size matches the exact number of structurally sound lines parsed from the resource file.
    - Display-name resource keys are evaluated as non-null and non-empty.
    - Initialization sequence throws:
        - `IllegalStateException` when the properties file cannot be found on the classpath.
        - `RuntimeException` if a hardware or stream IO disruption occurs.

---

- Step 2, BVA catalog mapping from the BVA catalog:
    - File size/entry count uses `Counters/Collections`:
        - 0 entries (empty)
        - 2 entries (minimum required threshold)
        - N entries (arbitrary valid populated pool)
    - Resource bundle string values use `Strings`:
        - null reference
        - empty string `""`
        - blank string `"   "`
        - normal valid alphanumeric string
    - File presence uses `Pointers/Resources`:
        - valid file reference on classpath
        - missing resource reference (`null` stream)
    - Return payload mutability uses `Immutability/State`:
        - unsupported mutation operations on returned Map

---

- Step 3, concrete boundary values selected from the catalog:
    - Entry quantity boundaries:
        - 0 entries (empty file)
        - 2 entries (`en-US=language.english`, `es-US=language.spanish`)
    - String validation boundaries:
        - `locale-tag=` (empty value entry)
        - `locale-tag=   ` (whitespace-only entry)
    - Classpath boundaries:
        - File present at `src/main/resources/supported-locales.properties`
        - File missing or renamed
    - Mutability boundaries:
        - Attempting `.put()`, `.clear()`, or `.remove()` on the map returned by `LocaleLoader.getSupportedLocales()`

---

- Step 4 strategy:
    - Use each-choice coverage for resource state configuration anomalies.
    - Use behavioral and exception assertions to check class loading constraints.
    - Use mutability guardrail tests to protect the collection against programmatic interference.
    - Avoid testing native Java core internals (like verifying if `Properties.load()` works), focusing instead on how `LocaleLoader` handles the parsed output.

---

## Method under test: `getSupportedLocales()`

|             | System under test | Expected output | Implemented?       |
|-------------|-------------------|-----------------|--------------------|
| Test Case 1 | `supported-locales.properties` has 2 valid entries | Map size is exactly 2, keys match parsed target locales, values match display keys | :white_check_mark: |
| Test Case 2 | `supported-locales.properties` is missing from classpath | Static initialization fails, throwing `IllegalStateException` | :white_check_mark: |
| Test Case 3 | `supported-locales.properties` exists but is completely empty | Map returns successfully but is empty (size 0) | :white_check_mark: |
| Test Case 4 | Key exists but has an empty value (e.g., `en-US=`) | Map includes the locale but maps to an empty string `""` (violating display-name constraints) | :white_check_mark: |
| Test Case 5 | Key exists but has only spaces (e.g., `en-US=\u0020\u0020`) | Map includes the locale but maps to a blank string `"  "` | :white_check_mark: |
| Test Case 6 | Attempt to modify the map via `getSupportedLocales().put(newLocale, key)` | Throws `UnsupportedOperationException` (guaranteeing immutability) | :x:                |