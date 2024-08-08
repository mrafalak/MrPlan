package com.mr.presentation.ui.components.bars

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mr.presentation.navigation.MrNavigator
import com.mr.presentation.navigation.actions.TopBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MrTopBar(
    navigator: MrNavigator? = null,
    config: TopBarConfig = TopBarConfig()
) {
    val onNavigationAction =
        if (config.isNavigationEnabled && navigator != null && navigator.canPop) {
            TopBarAction.NavigationBack(navigator)
        } else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    ) {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                if (config.titleResId != null) {
                    Text(
                        text = stringResource(id = config.titleResId),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            navigationIcon = {
                if (onNavigationAction != null) {
                    IconButton(
                        onClick = {
                            onNavigationAction.onClick()
                        }
                    ) {
                        Icon(
                            imageVector = onNavigationAction.icon,
                            contentDescription = stringResource(id = onNavigationAction.titleResId)
                        )
                    }
                }
            },
            actions = {
                if (config.actions.size > 3) {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        config.actions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = option.icon,
                                            contentDescription = null
                                        )
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = stringResource(id = option.titleResId)
                                        )
                                    }
                                },
                                onClick = {
                                    expanded = false
                                    option.onClick()
                                }
                            )
                        }
                    }
                } else {
                    config.actions.forEach { option ->
                        IconButton(onClick = {
                            option.onClick()
                        }) {
                            Icon(imageVector = option.icon, contentDescription = null)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}