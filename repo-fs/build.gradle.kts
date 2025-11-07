plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenLocal()
    mavenCentral()
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
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
