package com.imaec.harudiary

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureAndroidCommon(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    with(commonExtension) {
        compileSdk = 35

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildFeatures {
            buildConfig = true
        }
    }
}

internal fun Project.configureAndroidApp(
    baseAppModuleExtension: BaseAppModuleExtension
) {
    with(baseAppModuleExtension) {
        defaultConfig {
            applicationId = "com.imaec.harudiary"
            minSdk = 21
            targetSdk = 35
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
}

internal fun Project.configureAndroid(
    extension: KotlinAndroidProjectExtension,
) {
    with(extension) {
        compilerOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors.set(
                properties["warningsAsErrors"] as? Boolean ?: false
            )

            freeCompilerArgs.set(
                freeCompilerArgs.getOrElse(emptyList()) + listOf(
                    "-Xcontext-receivers",
                    "-Xopt-in=kotlin.RequiresOptIn",
                    // Enable experimental coroutines APIs, including Flow
                    "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    // Enable experimental compose APIs
                    "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
                    "-Xopt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                )
            )

            // Set JVM target to 17
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}
