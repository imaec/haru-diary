plugins {
    alias(libs.plugins.harudiary.android.feature)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.feature.languagesetting"
}

dependencies {

    // library
    implementation(libs.androidx.appcompat)
}
