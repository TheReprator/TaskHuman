
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

includeBuild("compositeBuild")

include(
    ":app",
    ":base",
    ":base-android",
    ":navigation",
    ":appModules:hostAvailability"
)
rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "TaskHuman"
