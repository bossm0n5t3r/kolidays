import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val kolidaysVersion: String by project

plugins {
    java
    kotlin("jvm") version "1.8.20"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.bossm0n5t3r"
            artifactId = "kolidays"
            version = kolidaysVersion

            from(components["java"])
        }
    }
}
