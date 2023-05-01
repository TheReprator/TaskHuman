plugins {
    id("appComponentPlugin")
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {
    implementation(projects.baseAndroid)

    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
}
