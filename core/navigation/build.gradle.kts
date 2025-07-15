plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.imaec.core.navigation"
}

dependencies {

    // library
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    api(libs.androidx.navigation.compose)
}
