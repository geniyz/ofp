plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.kover)
    application
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
    implementation(libs.kotlinx.serialization.core)
    implementation (libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.serialization.core)
    testImplementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.server.cio)
    testImplementation(libs.ktor.server.test.host)

    implementation(libs.logback.classic)

    implementation(libs.koin.ktor)
    implementation(libs.koin.slf4j)

    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    implementation(libs.reflections)
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

// перед запуском тестов собрать проект rules сложить его артефакты в libs -- необходимо для тестирования Loader
val copyRulesLibs by tasks.register<Copy>("copyRulesLibs") {
    dependsOn(":rules:build")
    from(project(":rules").layout.buildDirectory.dir("libs"))
    into(rootProject.layout.projectDirectory.dir("addons"))
    include("*.jar")
}
tasks.named("test") {
    dependsOn(":rules:build", copyRulesLibs)
}
