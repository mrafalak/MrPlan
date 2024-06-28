package extensions

import com.android.build.api.dsl.CommonExtension
import config.Config
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureAndroidKotlin(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = Config.android.compileSdkVersion

        defaultConfig {
            minSdk = Config.android.minSdkVersion
        }

        compileOptions {
            sourceCompatibility = Config.jvm.javaVersion
            targetCompatibility = Config.jvm.javaVersion
        }
    }

    with(extensions.getByType<KotlinAndroidProjectExtension>()) {
        compilerOptions {
            jvmTarget = Config.jvm.kotlinJvm
            freeCompilerArgs.addAll(Config.jvm.freeCompilerArgs)
        }
    }
}