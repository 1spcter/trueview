import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version "1.2.12"
    java
}

group = "com.trueview"
version = "1.0.0"
val minecraftVersion = "1.26.3"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$minecraftVersion+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.20.0")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.95.0+1.26.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

loom {
    log4j2.disablePlugin()
}
