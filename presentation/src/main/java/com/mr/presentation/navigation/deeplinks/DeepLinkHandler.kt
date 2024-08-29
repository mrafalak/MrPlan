package com.mr.presentation.navigation.deeplinks

import android.net.Uri
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import com.mr.presentation.home.base.HomeScreen
import com.mr.presentation.main.MainActivityState
import com.mr.presentation.main.MainViewModel
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.LoadingScreen
import com.mr.presentation.welcome.WelcomeScreen

class DeepLinkHandler(
    private val navigator: Navigator,
    private val mainViewModel: MainViewModel
) {

//    fun handleDeepLink(state: MainActivityState, deepLink: Uri) {
//        if (state.lastDeepLink == deepLink) {
//            mainViewModel.resetLastDeepLinkWithDelay()
//            return
//        } else {
//            mainViewModel.updateLastDeepLink(deepLink)
//            deepLink.path?.let { path ->
//                when {
//                    path.contains(MainDeepLinkPath.Home.path) -> navigateToScreen(
//                        state = state,
//                        screen = HomeScreen(deepLinkPath = path)
//                    )
//
//                    else -> {
//                        if (navigator.lastItem is LoadingScreen && state.initialScreen is LoadingScreen) {
//                            println("DeepLink - setInitialScreen default WelcomeScreen")
//                            mainViewModel.setInitialScreen(WelcomeScreen())
//                        } else {
//                            println("DeepLink - open app only")
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun navigateToScreen(state: MainActivityState, screen: AndroidScreen) {
//        if (state.initialScreen is LoadingScreen) {
//            println("DeepLink - setInitialScreen ${screen.uniqueScreenKey}")
//            mainViewModel.setInitialScreen(screen)
//        } else {
//            println("DeepLink - replace ${screen.uniqueScreenKey}")
//            navigator.replace(screen)
//        }
//        mainViewModel.resetLastDeepLinkWithDelay()
//    }
}