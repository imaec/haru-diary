plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.core.worker"
}

dependencies {

    // module
    api(project(":domain"))
    implementation(project(":core:utils"))

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.work.compiler)
}
