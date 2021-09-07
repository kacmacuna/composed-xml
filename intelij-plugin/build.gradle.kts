plugins {
    kotlin("jvm")
    id("org.jetbrains.intellij")
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(kotlin("stdlib"))
    implementation("com.squareup:kotlinpoet:1.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
}
tasks {
    instrumentCode {
        compilerVersion.set("211.7628.21")
    }
    patchPluginXml {
        sinceBuild.set("193")
        untilBuild.set("220.*")
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}