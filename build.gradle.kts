plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
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