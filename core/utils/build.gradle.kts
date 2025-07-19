plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.library.compose)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.core.utils"
}

dependencies {

    // module
    implementation(project(":core:resource"))

    // library
    implementation(libs.accompanist.permissions)
}
