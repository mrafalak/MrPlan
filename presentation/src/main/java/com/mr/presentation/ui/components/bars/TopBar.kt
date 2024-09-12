package com.mr.presentation.ui.components.bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.home.base.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(topBarState: TopBarState) {
    val animDuration = UiConfig.NAVIGATION_ANIM_DURATION

    AnimatedVisibility(
        visible = topBarState !is TopBarState.None,
        enter = fadeIn(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideInVertically(
            animationSpec = tween(durationMillis = animDuration)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = animDuration)
        ) + slideOutVertically(
            animationSpec = tween(durationMillis = animDuration)
        )
    ) {
        val config: TopBarConfig = when (topBarState) {
            is TopBarState.None -> TopBarConfig()
            is TopBarState.Visible -> topBarState.config
        }

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
                    if (config.navigationAction != null) {
                        IconButton(
                            onClick = {
                                config.navigationAction.onClick()
                            }
                        ) {
                            Icon(
                                imageVector = config.navigationAction.icon,
                                contentDescription = stringResource(id = config.navigationAction.titleResId)
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
}