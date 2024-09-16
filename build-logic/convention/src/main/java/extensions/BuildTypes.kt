package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import config.ENABLE_ANALYTICS
import config.ENABLE_CRASHLYTICS
import org.gradle.api.Project

internal fun Project.configureAppBuildTypes(
    appExt: ApplicationExtension,
) {
    appExt.apply {
        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro")
                )
                buildConfigField("boolean", ENABLE_CRASHLYTICS, "true")
                buildConfigField("boolean", ENABLE_ANALYTICS, "true")
            }
            getByName("debug") {
                isMinifyEnabled = false
                isShrinkResources = false
                buildConfigField("boolean", ENABLE_CRASHLYTICS, "false")
                buildConfigField("boolean", ENABLE_ANALYTICS, "false")
            }
        }
    }
}

internal fun Project.configureLibraryBuildTypes(
    libExt: LibraryExtension,
) {
    libExt.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro")
                )
                buildConfigField("boolean", ENABLE_CRASHLYTICS, "true")
                buildConfigField("boolean", ENABLE_ANALYTICS, "true")
            }
            getByName("debug") {
                isMinifyEnabled = false
                buildConfigField("boolean", ENABLE_CRASHLYTICS, "false")
                buildConfigField("boolean", ENABLE_ANALYTICS, "false")
            }
        }
    }
}