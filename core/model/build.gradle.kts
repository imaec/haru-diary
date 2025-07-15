plugins {
    alias(libs.plugins.harudiary.android.library)
}

android {
    namespace = "com.imaec.core.model"
}

dependencies {

    // module
    implementation(project(":domain"))
}
