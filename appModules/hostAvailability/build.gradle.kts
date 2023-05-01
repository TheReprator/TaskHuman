plugins {
    id("appComponentPlugin")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(projects.base)
    implementation(projects.baseAndroid)
    implementation(projects.navigation)

    implementation(libs.androidx.widget.cardView)
    implementation(libs.androidx.widget.swiperefresh)

    implementation(libs.androidx.navigation.fragment)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}
