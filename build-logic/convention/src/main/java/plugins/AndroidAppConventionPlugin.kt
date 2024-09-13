package plugins

import com.android.build.api.dsl.ApplicationExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import extensions.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidAppConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            dependencies {
                "implementation"(versionCatalog().findLibrary("hilt-android").get())
                "ksp"(versionCatalog().findLibrary("hilt-android-compiler").get())
                "implementation"(versionCatalog().findLibrary("timber-core").get())
                "implementation"(platform(versionCatalog().findLibrary("google-firebase-bom").get()))
                "implementation"(versionCatalog().findLibrary("google-firebase-analytics").get())
                "implementation"(versionCatalog().findLibrary("google-firebase-crashlytics").get())
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidKotlin(this)
                configureBuildTypes(this)

                defaultConfig {
                    applicationId = Config.android.applicationId
                    targetSdk = Config.android.targetSdkVersion
                    versionCode = Config.android.versionCode
                    versionName = Config.android.versionName
                }

                buildFeatures.buildConfig = true
            }
        }
    }
}