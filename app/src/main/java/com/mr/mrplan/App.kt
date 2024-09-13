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
        if (BuildConfig.DEBUG) {
            firebaseCrashlytics.isCrashlyticsCollectionEnabled = false
            firebaseAnalytics.setAnalyticsCollectionEnabled(false)
        } else {
            firebaseCrashlytics.isCrashlyticsCollectionEnabled = true
            firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        }
    }
}