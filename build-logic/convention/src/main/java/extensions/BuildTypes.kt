package extensions

import com.android.build.api.dsl.CommonExtension
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
            }
            getByName("debug") {
                isMinifyEnabled = false
            }
        }
    }
}