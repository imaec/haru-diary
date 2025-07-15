plugins {
    alias(libs.plugins.harudiary.android.feature)
    alias(libs.plugins.harudiary.android.hilt)
}

android {
    namespace = "com.imaec.feature.main"
}

dependencies {

    // module
    implementation(project(":feature:home"))
    implementation(project(":feature:my"))
}
