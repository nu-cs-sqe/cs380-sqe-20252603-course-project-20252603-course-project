import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort

plugins {
    id("java")
    checkstyle
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.spotbugs") version "6.5.4"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-launcher")
    // Source: https://mvnrepository.com/artifact/org.easymock/easymock
    testImplementation("org.easymock:easymock:5.4.0")
}

application {
    mainModule.set("risk")
    mainClass.set("ui.Main")
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-noframes-severity-sorted.xsl")
    }
}

checkstyle {
    toolVersion = "10.23.0"
    configFile = file("config/checkstyle/sun_checks.xml")
    isIgnoreFailures = false
}

// Use a relaxed Checkstyle config for test sources (allow underscores and magic numbers)
tasks.named<Checkstyle>("checkstyleTest") {
    configFile = file("config/checkstyle/sun_checks_test.xml")
}

tasks.register<Exec>("jlink") {
    dependsOn("jar")
    commandLine(
        "${System.getProperty("java.home")}/bin/jlink",
        "--module-path", "${System.getProperty("java.home")}/jmods:${configurations.runtimeClasspath.get().asPath}:build/libs/sqe-course-project-1.0.jar",
        "--add-modules", "risk,javafx.controls,javafx.graphics,javafx.base",
        "--output", "build/image",
        "--launcher", "Risk=risk/ui.Main",
        "--strip-debug",
        "--compress", "2",
        "--no-header-files",
        "--no-man-pages"
    )
}
spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.HIGH
    //omitVisitors = listOf("FindNonShortCircuit")
    reportsDir = file("spotbugs")
    //onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
    //jvmArgs = listOf("-Duser.language=ja") // set user language to japanese
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}
