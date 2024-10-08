package plugins

import com.android.build.gradle.LibraryExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureLibraryBuildTypes
import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            dependencies {
                "implementation"(versionCatalog().findLibrary("hilt-android").get())
                "ksp"(versionCatalog().findLibrary("hilt-android-compiler").get())
                "implementation"(versionCatalog().findLibrary("timber-core").get())
                "implementation"(
                    platform(
                        versionCatalog().findLibrary("google-firebase-bom").get()
                    )
                )
                "implementation"(versionCatalog().findLibrary("google-firebase-analytics").get())
                "implementation"(versionCatalog().findLibrary("google-firebase-crashlytics").get())
            }

            extensions.configure<LibraryExtension> {
                configureAndroidKotlin(this)
                configureLibraryBuildTypes(this)

                defaultConfig.targetSdk = Config.android.targetSdkVersion

                buildFeatures.buildConfig = true
            }
        }
    }
}