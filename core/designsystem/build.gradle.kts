plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.library.compose)
}

android {
    namespace = "com.imaec.core.designsystem"
}

dependencies {

    // module
    implementation(project(":domain"))
    api(project(":core:resource"))
    api(project(":core:utils"))
    implementation(project(":core:navigation"))
    implementation(project(":core:model"))

    // library
    implementation(libs.androidx.biometric)
}
