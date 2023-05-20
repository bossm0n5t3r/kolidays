plugins {
    kotlin("jvm") version "1.8.20"

    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("com.github.kittinunf.fuel:fuel:3.0.0-alpha1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0-beta-27")
    implementation("org.slf4j:slf4j-simple:2.0.7")

    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

/**
 * https://docs.gradle.org/current/userguide/working_with_files.html#sec:creating_uber_jar_example
 */
tasks.register<Jar>("buildJar") {
    manifest {
        attributes["Main-Class"] = "me.bossm0n5t3r.kolidays.batch.MainKt"
    }

    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
