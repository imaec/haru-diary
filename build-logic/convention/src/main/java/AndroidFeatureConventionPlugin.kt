import com.android.build.gradle.LibraryExtension
import com.imaec.harudiary.configureAndroid
import com.imaec.harudiary.configureAndroidCommon
import com.imaec.harudiary.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<LibraryExtension> {
                configureAndroidCommon(this)
                configureCompose(this)
                defaultConfig.targetSdk = 35
            }

            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                configureAndroid(this)
            }

            dependencies {
                add("implementation", project(":domain"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:model"))

                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
            }
        }
    }
}
