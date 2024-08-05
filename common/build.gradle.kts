plugins {
    alias(libs.plugins.mrplan.android.library)
    alias(libs.plugins.mrplan.android.lib.compose)
}

android {
    namespace = "com.mr.common"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.ui.all)

    implementation(libs.bundles.voyager.all)
}