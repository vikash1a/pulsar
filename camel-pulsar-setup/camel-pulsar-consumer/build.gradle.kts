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
    implementation("org.apache.camel:camel-core:4.2.0")
    implementation("org.apache.camel:camel-pulsar:4.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}