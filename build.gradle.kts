plugins {
    kotlin("jvm") version "1.8.20"
}

group = "dev.bentoboxen.beatbot4"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://m2.dv8tion.net/releases")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.sedmelluq:lavaplayer:1.3.77")
    implementation("dev.kord:kord-core:0.8.3") {
        capabilities {
            requireCapability("dev.kord:core-voice:0.8.3")
        }
    }
    implementation("dev.kord:kord-voice:0.8.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0-beta-22")
}