plugins {
    alias(libs.plugins.mrplan.android.library)
    alias(libs.plugins.mrplan.android.lib.compose)
}

android {
    namespace = "com.mr.presentation"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.ui.all)

    implementation(libs.bundles.voyager.all)
}