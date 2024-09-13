package extensions

import com.android.build.api.dsl.CommonExtension
import config.ENABLE_ANALYTICS
import config.ENABLE_CRASHLYTICS
import org.gradle.api.Project

internal fun Project.configureBuildTypes(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
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