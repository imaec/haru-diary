plugins {
    alias(libs.plugins.harudiary.android.library)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.local"
}

dependencies {

    // module
    implementation(project(":data"))

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.datastore.preferences)
}
