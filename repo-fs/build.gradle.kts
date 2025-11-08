plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
}

repositories {
    mavenLocal()
    mavenLocal()

    maven {                                           // remove on RBN
        url = uri("https://mvn.geniyz.site/releases") // remove on RBN
        isAllowInsecureProtocol = true                // remove on RBN
    }                                                 // remove on RBN

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
