plugins {
    id("net.fabricmc.loom") version "1.18.2"
    java
}

group = "com.trueview"
version = "1.0.0"
val minecraftVersion = "1.26.3"

repositories {
    // Fabric artifacts and Loom plugin artifacts
    maven { url = uri("https://maven.fabricmc.net/") }
    mavenCentral()
}

dependencies {
    // Minecraft
    minecraft("com.mojang:minecraft:$minecraftVersion")

    // Use Mojang official mappings for 1.26.3 (Yarn is not available for this MC version)
    mappings(loom.officialMojangMappings())

    // Fabric Loader and Fabric API versions that match Minecraft 1.26.3
    modImplementation("net.fabricmc:fabric-loader:0.19.5")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.163.0+26.3")

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
