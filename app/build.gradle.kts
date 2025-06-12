val koinVersion = "4.0.0"
val ktorVersion = "3.1.3"
val serialize   = "1.8.1"

plugins {
    val kotlinVersion = "2.1.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
    application
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialize")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialize")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialize")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialize")

    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:1.5.18")

    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
    testImplementation("org.mockito:mockito-inline:4.8.0")

    implementation("org.reflections:reflections:0.10.2")
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
    into(layout.projectDirectory.dir("libs"))
    include("*.jar")
}
tasks.named("test") {
    dependsOn(":rules:build", copyRulesLibs)
}
