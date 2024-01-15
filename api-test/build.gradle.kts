plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.padoru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
	implementation(dependencyNotation = "com.github.auties00:whatsappweb4j:3.5.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}