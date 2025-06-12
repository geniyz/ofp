val koinVersion = "4.0.0"
val serialize   = "1.8.1"
val ioCore      = "0.7.0"

plugins {
    val kotlinVersion = "2.1.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

dependencies {

    implementation(project(":app"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialize")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialize")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialize")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialize")

    implementation("org.jetbrains.kotlinx:kotlinx-io-core:$ioCore")

    testImplementation("io.insert-koin:koin-test:${koinVersion}")
    testImplementation("io.insert-koin:koin-test-junit4:${koinVersion}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
