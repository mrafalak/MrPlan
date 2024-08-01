plugins {
    alias(libs.plugins.mrplan.android.library)
}

android {
    namespace = "com.mr.presentation"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.ui.all)
}