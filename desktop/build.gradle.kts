plugins {
    kotlin("jvm")
    java
    id("org.jetbrains.compose") version "1.0.0-alpha3"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

version = "unspecified"

dependencies {
    implementation(project(":core"))
    implementation(compose.desktop.currentOs)
    implementation("com.squareup:kotlinpoet:1.9.0")
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}