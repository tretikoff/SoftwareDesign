import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktlint by configurations.creating

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.dokka") version "1.4.20"
    id("io.gitlab.arturbosch.detekt") version "1.16.0-RC1"
    application
}

group = "me.professional"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter() // or maven(url="https://dl.bintray.com/kotlin/dokka")
}

dependencies {
    testImplementation(kotlin("test-junit"))
    ktlint("com.pinterest:ktlint:0.40.0")
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled =
            true // similar to the console output, contains issue signature to manually edit baseline files
        sarif.enabled =
            true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    }
}


val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val lint by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}

val file: String by project
val execute by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    if (project.hasProperty("file")) {
        args = mutableListOf(file)
    }
    main = "MainKt"
    classpath = sourceSets["main"].runtimeClasspath
}

val format by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
}


dependencies {
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.4.20")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}
