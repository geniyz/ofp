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

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlinx.serialization.core)
    testImplementation(libs.kotlinx.serialization.json)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
