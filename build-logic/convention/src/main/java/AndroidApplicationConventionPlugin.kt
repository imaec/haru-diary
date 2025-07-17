import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.imaec.harudiary.configureAndroid
import com.imaec.harudiary.configureAndroidCommon
import com.imaec.harudiary.configureAndroidApp
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
                namespace = "com.imaec.harudiary"

                configureAndroidCommon(this)
                configureAndroidApp(this)
            }

            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                configureAndroid(this)
            }

            dependencies {
                add("implementation", project(":domain"))
                add("implementation", project(":data"))
                add("implementation", project(":local"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":feature:main"))
                add("implementation", project(":feature:write"))
                add("implementation", project(":feature:diarylist"))

                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.bom").get())
                add("implementation", libs.findLibrary("androidx.material3.android").get())
                add("implementation", libs.findLibrary("androidx.room.runtime").get())
                add("ksp", libs.findLibrary("androidx.room.compiler").get())
                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("datastore.preferences").get())
            }
        }
    }
}
