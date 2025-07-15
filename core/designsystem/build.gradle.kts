plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.library.compose)
}

android {
    namespace = "com.imaec.core.designsystem"
}

dependencies {

    // module
    api(project(":core:resource"))
    api(project(":core:utils"))
}
