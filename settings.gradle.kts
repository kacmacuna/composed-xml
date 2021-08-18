pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.21"
        id("org.jetbrains.intellij") version "1.1.4"
    }
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "composed-xml"
include("intelij-plugin")
include("core")
include("desktop")
