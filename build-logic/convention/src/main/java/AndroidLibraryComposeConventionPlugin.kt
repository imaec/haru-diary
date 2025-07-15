import com.android.build.gradle.LibraryExtension
import com.imaec.harudiary.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            val extension = extensions.getByType<LibraryExtension>()
            configureCompose(extension)

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("api", platform(libs.findLibrary("androidx.compose.bom").get()))
                add("api", libs.findLibrary("androidx.material3.android").get())
                add("api", libs.findLibrary("androidx.ui.tooling").get())
                add("api", libs.findLibrary("androidx.ui.tooling.preview").get())
            }
        }
    }
}
