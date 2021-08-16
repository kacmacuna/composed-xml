pluginManagement {
    plugins {
        kotlin("jvm") version "1.5.10"
        id("org.jetbrains.intellij") version "1.1.4"
    }
}

rootProject.name = "composed-xml"
include("intelij-plugin")
include("core")
