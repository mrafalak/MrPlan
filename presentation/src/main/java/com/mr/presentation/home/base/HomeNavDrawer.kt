package com.mr.presentation.home.base

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mr.presentation.R
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.settings.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeNavDrawer(
    modifier: Modifier = Modifier,
    state: DrawerState,
    scope: CoroutineScope,
    navigator: MrNavigator
) {
    ModalDrawerSheet(modifier = modifier) {
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.nav_settings)) },
            selected = false,
            onClick = {
                navigator.push(SettingsScreen())
                scope.launch {
                    state.close()
                }
            }
        )
    }
}