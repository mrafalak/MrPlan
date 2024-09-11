package com.mr.presentation.navigation.actions

import android.app.Activity
import cafe.adriel.voyager.core.screen.Screen
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.ui.AndroidScreen

fun navigateBack(
    activity: Activity,
    navigator: MrNavigator,
    defaultNavBackScreen: AndroidScreen? = null
) {
    if (navigator.canPop) {
        navigator.pop()
    } else if (defaultNavBackScreen != null) {
        navigator.replace(defaultNavBackScreen as Screen)
    } else {
        activity.finish()
    }
}