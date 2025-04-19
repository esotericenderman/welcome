import org.gradle.api.JavaVersion
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
  java
  `java-library`

  `maven-publish`

  id("io.papermc.paperweight.userdev") version "1.7.2"
  id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.1.1"
  id("xyz.jpenilla.run-paper") version "2.3.0"
}

description = "A plugin to display a title and a changing subtitle."

group = "dev.enderman"
version = "0.0.15"

val javaVersion = 17
val javaVersionEnum = JavaVersion.valueOf("VERSION_$javaVersion")

val minecraftVersion = "1.20"
val apiVersion = "$minecraftVersion-R0.1-SNAPSHOT"

java {
  sourceCompatibility = javaVersionEnum
  targetCompatibility = javaVersionEnum

  toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}

dependencies {
  paperweight.paperDevBundle(apiVersion)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = group.toString()
            artifactId = rootProject.name
            version = project.version.toString()
        }
    }
}

tasks {
  compileJava {
    options.release = javaVersion
  }

  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }

  named("publishMavenJavaPublicationToMavenLocal") {
    dependsOn(build)
  }
}

bukkitPluginYaml {
  name = "Welcome"
  description = project.description
  authors = listOf("Esoteric Enderman")
  website = "https://github.com/esotericenderman/welcome"

  version = project.version.toString()
  apiVersion = minecraftVersion
  main = group.toString() + ".commissions.minecraft.plugins.welcome." + "${name.get()}Plugin"
  load = BukkitPluginYaml.PluginLoadOrder.STARTUP
}
