plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {

    // library
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.hilt.core)
    implementation(libs.coroutines)
}
