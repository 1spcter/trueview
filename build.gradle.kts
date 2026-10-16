plugins {
    java
    id("net.fabricmc.loom") version "1.17"
}

group = "com.trueview"
version = "1.0.0"
val minecraftVersion = "1.26.3"

repositories {
    mavenCentral()
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
    javaVersion.set(21)
    mixin {
        defaultRefmapName.set("trueview.refmap.json")
    }
}
