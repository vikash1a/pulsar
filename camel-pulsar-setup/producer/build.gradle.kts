plugins {
    kotlin("jvm") version "2.0.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.pulsar:pulsar-client:4.0.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}