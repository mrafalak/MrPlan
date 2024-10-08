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
import com.mr.presentation.profile.ProfileScreen
import com.mr.presentation.settings.SettingsScreen
import com.mr.presentation.ui.AndroidScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeNavDrawer(
    modifier: Modifier = Modifier,
    state: DrawerState,
    scope: CoroutineScope,
    navigator: MrNavigator
) {
    val screenItems: Map<Int, AndroidScreen> = mapOf(
        R.string.nav_profile to ProfileScreen(),
        R.string.nav_settings to SettingsScreen()
    )

    ModalDrawerSheet(modifier = modifier) {
        screenItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(stringResource(id = item.key)) },
                selected = false,
                onClick = {
                    navigator.push(item.value)
                    scope.launch {
                        state.close()
                    }
                }
            )
        }
    }
}