plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

dependencies {

    implementation(project(":app"))

    implementation(libs.kotlinx.serialization.core)
    implementation (libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.serialization.core)
    testImplementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlinx.io.core)

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.h2)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.jar { // вкомпилять в библиотеку зависимости:
    from(
        configurations
            .runtimeClasspath
            .get()
            .filter {
                it.absolutePath.contains("exposed") ||
                        it.absolutePath.contains("h2database") ||
                        it.absolutePath.contains("postgresql")
            }
            .map {
                if (it.isDirectory) it
                else zipTree(it)
            }
    )
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
