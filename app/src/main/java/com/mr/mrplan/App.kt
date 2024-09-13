package com.mr.mrplan

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var firebaseCrashlytics: FirebaseCrashlytics

    override fun onCreate() {
        super.onCreate()
        configureFirebase()
    }

    private fun configureFirebase() {
        // TODO ask user for permission to send analytics data
        firebaseCrashlytics.isCrashlyticsCollectionEnabled = BuildConfig.ENABLE_CRASHLYTICS
        firebaseAnalytics.setAnalyticsCollectionEnabled(BuildConfig.ENABLE_ANALYTICS)
    }
}