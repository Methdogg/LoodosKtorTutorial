val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_sql_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "com.emirhanemmez"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-auth:1.6.6")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_sql_version")
    runtimeOnly("org.jetbrains.exposed:exposed-jdbc:$exposed_sql_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_sql_version")
    implementation("org.postgresql:postgresql:42.3.1")

    implementation("io.ktor:ktor-jackson:$ktor_version")

    implementation("at.favre.lib:bcrypt:0.9.0")

    implementation("io.ktor:ktor-auth-jwt:$ktor_version")

    implementation("io.insert-koin:koin-ktor:3.1.4")
}