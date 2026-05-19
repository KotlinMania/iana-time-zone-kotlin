pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins { kotlin("multiplatform") version "2.3.21" }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "iana-time-zone-kotlin"

val androidSystemPropertiesBuild = file("../android-system-properties-kotlin")
if (androidSystemPropertiesBuild.isDirectory) {
    includeBuild(androidSystemPropertiesBuild)
}
