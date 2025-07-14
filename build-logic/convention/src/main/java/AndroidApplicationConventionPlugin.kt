import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.imaec.harudiary.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jlleitschuh.gradle.ktlint")
                apply("com.google.devtools.ksp")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(this)
                defaultConfig.targetSdk = 35
            }

            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                configureAndroid(this)
            }

            dependencies {
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.bom").get())
                add("implementation", libs.findLibrary("androidx.material3.android").get())
            }
        }
    }
}
