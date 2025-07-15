plugins {
    alias(libs.plugins.harudiary.android.application)
    alias(libs.plugins.harudiary.android.application.compose)
}

android {
    namespace = "com.imaec.harudiary"

    defaultConfig {
        applicationId = "com.imaec.harudiary"
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

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
