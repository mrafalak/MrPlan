plugins {
    alias(libs.plugins.mrplan.android.library)
}

android {
    namespace = "com.mr.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.google.firebase.auth)
}