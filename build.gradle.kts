import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `maven-publish`

  kotlin("jvm") version "1.5.10"
  kotlin("plugin.serialization") version "1.5.0"
}

group = "gg.mixtape"
version = "1.0.0"
val archivesBaseName = "kokusaika"

/* dependencies */
repositories {
  mavenCentral()
}

dependencies {
  /* kotlin libraries */
  implementation(kotlin("stdlib"))
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  /* kotlin serialization */
  api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
  
  /* logging */
  api("org.slf4j:slf4j-api:1.7.30")
}

/* jfrog bullshit */
val sourcesJar = task<Jar>("sourcesJar") {
  archiveClassifier.set("sources")
  from(sourceSets["main"].allJava)
}

publishing {
  repositories {
    maven {
      name = "jfrog"
      url = uri("https://dimensional.jfrog.io/artifactory/maven")
      credentials {
        username = System.getenv("JFROG_USERNAME")
        password = System.getenv("JFROG_PASSWORD")
      }
    }
  }

  publications {
    create<MavenPublication>("jfrog") {
      from(components["java"])

      group = project.group as String
      version = project.version as String
      artifactId = archivesBaseName

      artifact(sourcesJar)
    }
  }
}

tasks.build {
  dependsOn(tasks.jar)
  dependsOn(sourcesJar)
}

tasks.publish {
  dependsOn(tasks.build)
  onlyIf {
    System.getenv("JFROG_USERNAME") != null && System.getenv("JFROG_PASSWORD") != null
  }
}

/* kotlin compile */
tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "16"
  kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
}
