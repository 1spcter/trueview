plugins {
    id("net.fabricmc.loom") version "1.17"
    java
}

group = "com.trueview"
version = "1.0.0"
val minecraftVersion = "1.26.3"

repositories {
    mavenCentral()
}

dependencies {
    // Use add(...) to avoid Kotlin DSL unresolved reference issues when plugin provides configuration names
    add("minecraft", "com.mojang:minecraft:$minecraftVersion")
    add("mappings", "net.fabricmc:yarn:$minecraftVersion+build.1:v2")
    add("modImplementation", "net.fabricmc:fabric-loader:0.20.0")
    add("modImplementation", "net.fabricmc.fabric-api:fabric-api:0.95.0+1.26.3")
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
    mixin {
        defaultRefmapName.set("trueview.refmap.json")
    }
}
