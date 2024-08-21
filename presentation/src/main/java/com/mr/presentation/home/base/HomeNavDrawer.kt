package com.mr.presentation.home.base

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeNavDrawer(
    modifier: Modifier = Modifier,
    state: HomeState,
    scope: CoroutineScope,
    navigator: MrNavigator
) {
    ModalDrawerSheet(modifier = modifier) {
        NavigationDrawerItem(
            label = { Text("Welcome") },
            selected = false,
            onClick = {
                navigator.push(WelcomeScreen())
                scope.launch {
                    state.drawerState.close()
                }
            }
        )
    }
}