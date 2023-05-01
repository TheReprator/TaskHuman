plugins {
    id("appMainApplicationPlugin")
    id("dagger.hilt.android.plugin")
}

val qaImplementation by configurations

dependencies {
    implementation(projects.base)
    implementation(projects.baseAndroid)
    implementation(projects.appModules.hostAvailability)
    implementation(projects.navigation)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.lifecycle.process)

    qaImplementation(libs.chucker.library)

    qaImplementation(libs.debugdrawer.debugdrawer)
    qaImplementation(libs.debugdrawer.leakcanary)
    qaImplementation(libs.debugdrawer.retrofit)
    qaImplementation(libs.retrofit.mock)
    qaImplementation(libs.debugdrawer.timber)
    qaImplementation(libs.debugdrawer.okhttplogger)

    qaImplementation(libs.leakCanary)
    qaImplementation(libs.okhttp.loggingInterceptor)
}