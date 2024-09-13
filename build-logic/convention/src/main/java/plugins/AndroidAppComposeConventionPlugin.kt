package plugins

import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroidCompose
import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidAppComposeConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {
                "implementation"(
                    platform(
                        versionCatalog().findLibrary("androidx-compose-bom").get()
                    )
                )
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}