plugins {
    java
    "maven-publish"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "fr.qg"
version = "0.1"

repositories {
    // CENTRAL
    mavenCentral()
    maven("https://jitpack.io")
    
    // SPIGOT
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    
    // FACTION
    maven("https://repo.codemc.io/repository/maven-public")
    maven("https://repo.dmulloy2.net/repository/public/")
    
    // SPIGOT / BUNGEE
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    
    // DI
    implementation("org.codejargon.feather:feather:1.0")
    
    
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation("de.tr7zw:item-nbt-api:2.11.1")
    
    implementation("com.github.Revxrsal.Lamp:common:3.1.2")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.1.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}