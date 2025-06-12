val koinVersion = "4.0.0"
val ktorVersion = "3.1.3"
val serialize   = "1.8.1"

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

    testImplementation ("io.insert-koin:koin-test:${koinVersion}")
    testImplementation("io.insert-koin:koin-test-junit4:${koinVersion}")
    testImplementation("io.ktor:ktor-server-test-host:${ktorVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${serialize}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${serialize}")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
