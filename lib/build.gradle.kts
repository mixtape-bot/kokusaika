import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  idea
  `java-library`
  `maven-publish`

  kotlin("jvm") version "1.5.0"
  kotlin("plugin.serialization") version "1.5.0"
}

version = "1.0.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
  api("org.slf4j:slf4j-api:1.7.30")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "13"
  kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
}
