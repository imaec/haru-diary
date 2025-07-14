plugins {
    alias(libs.plugins.harudiary.android.application)
    alias(libs.plugins.harudiary.android.application.compose)
}

android {
    namespace = "com.imaec.harudiary"

    defaultConfig {
        applicationId = "com.imaec.harudiary"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
