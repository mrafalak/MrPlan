plugins {
    alias(libs.plugins.mrplan.android.application)
    alias(libs.plugins.mrplan.android.app.compose)
}

android {
    namespace = "com.mr.mrplan"
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.ui.all)
    implementation(libs.bundles.voyager.all)
}