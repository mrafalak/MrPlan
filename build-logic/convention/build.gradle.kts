import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.hilt.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = libs.plugins.mrplan.android.application.get().pluginId
            implementationClass = "plugins.AndroidAppConventionPlugin"
        }
        register("androidAppCompose") {
            id = libs.plugins.mrplan.android.app.compose.get().pluginId
            implementationClass = "plugins.AndroidAppComposeConventionPlugin"
        }
        register("androidLib") {
            id = libs.plugins.mrplan.android.library.get().pluginId
            implementationClass = "plugins.AndroidLibConventionPlugin"
        }
        register("androidLibCompose") {
            id = libs.plugins.mrplan.android.lib.compose.get().pluginId
            implementationClass = "plugins.AndroidLibComposeConventionPlugin"
        }
    }
}