plugins {
    alias(libs.plugins.mrplan.android.library)
}

android {
    namespace = "com.mr.domain"
}

dependencies {
    implementation(project(":common"))
}