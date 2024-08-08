package com.mr.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import com.mr.presentation.R

@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        label = { Text(tab.options.title) },
        icon = {
            Icon(
                painter = tab.options.icon
                    ?: painterResource(id = R.drawable.ic_error_24),
                contentDescription = null
            )
        }
    )
}