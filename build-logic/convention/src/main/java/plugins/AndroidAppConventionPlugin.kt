package plugins

import com.android.build.api.dsl.ApplicationExtension
import config.Config
import extensions.configureAndroidKotlin
import extensions.configureBuildTypes
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
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