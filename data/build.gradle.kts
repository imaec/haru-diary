plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.data"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // module
    api(project(":domain"))

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
}
