package com.mr.presentation.home.base

import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.mr.domain.model.DeepLink
import com.mr.domain.model.DeepLinkHomeDirection
import com.mr.domain.model.DeepLinkMainDirection
import com.mr.domain.model.HomeTabEnum
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.home.book.BookTab
import com.mr.presentation.home.goal.GoalTab
import com.mr.presentation.home.note.NoteTab
import com.mr.presentation.home.training.TrainingTab
import com.mr.presentation.main.MainEffect
import com.mr.presentation.navigation.providers.LocalMrNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
    error("No HomeViewModel provided")
}

class HomeScreen(
    private val deepLinkPath: String? = null
) : AndroidScreen() {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalMrNavigator.current
        val scope = rememberCoroutineScope()

        val bottomBarVisible by viewModel.bottomBarVisible.observeAsState(false)
        val topBarState by viewModel.topBarState.observeAsState(TopBarState.None)

        LaunchedEffect(key1 = deepLinkPath) {
            viewModel.determineInitialTab(deepLinkPath)
        }

//        val tab: Tab = determineInitialTab(bottomBarTabs, deepLinkPath)

//        LaunchedEffect(viewModel.effect) {
//            viewModel.effect.collectLatest {
//                when (it) {
//                    is HomeEffect.OnMenuClick -> {
//                        if (it.showMenu) {
//                            state.drawerState.open()
//                        } else {
//                            state.drawerState.close()
//                        }
//                    }
//
//                    else -> Unit
//                }
//            }
//        }

        CompositionLocalProvider(LocalHomeViewModel provides viewModel) {
            ModalNavigationDrawer(
                drawerContent = {
                    HomeNavDrawer(
                        state = state.drawerState,
                        scope = scope,
                        navigator = navigator
                    )
                },
                drawerState = state.drawerState
            ) {
                HomeTabNavigator(
                    tabs = state.bottomBarTabs,
                    tab = state.initialTab,
                    bottomBarVisible = bottomBarVisible,
                    topBarState = topBarState,
                    viewModel = viewModel
                )
            }
        }
    }

//    @Composable
//    private fun determineInitialTab(
//        deepLinkPath: String? = null
//    ): Tab {
//        return if (deepLinkPath == null) {
//            tabs.last() // TODO set to first()
//        } else {
//            val deepLink = DeepLink.parse(deepLinkPath)
//            when (deepLink.subPaths.first()) {
//                DeepLinkHomeDirection.GOAL.path -> {
//                    println("DeepLink - HomeScreen handle deep link")
//                    GoalTab(deepLink.fullPath)
//                }
//
//                DeepLinkHomeDirection.BOOK.path -> {
//                    BookTab()
//                }
//
//                DeepLinkHomeDirection.TRAINING.path -> {
//                    TrainingTab()
//                }
//
//                DeepLinkHomeDirection.NOTE.path -> {
//                    NoteTab()
//                }
//
//                else -> tabs.last() // TODO set to first()
//            }
//        }
//    }
}