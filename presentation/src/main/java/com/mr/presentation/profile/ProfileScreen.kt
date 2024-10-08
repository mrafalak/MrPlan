package com.mr.presentation.profile

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.test.FakeImage
import com.mr.domain.navigation.UiConfig
import com.mr.presentation.R
import com.mr.presentation.home.base.TopBarState
import com.mr.presentation.main.LocalActivity
import com.mr.presentation.navigation.actions.TopBarNavigationAction
import com.mr.presentation.navigation.providers.LocalMrNavigator
import com.mr.presentation.settings.DebugProfileState
import com.mr.presentation.settings.ProfileEffect
import com.mr.presentation.settings.ProfileState
import com.mr.presentation.settings.ProfileViewModel
import com.mr.presentation.ui.AndroidScreen
import com.mr.presentation.ui.components.bars.TopBar
import com.mr.presentation.ui.components.bars.TopBarConfig
import com.mr.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.flow.collectLatest

class ProfileScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalMrNavigator.current
        val context = LocalContext.current
        val activity = LocalActivity.current
        val viewModel: ProfileViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.setTopBarState(
                TopBarState.Visible(
                    navigator = navigator,
                    config = TopBarConfig(
                        titleResId = R.string.nav_profile,
                        navigationAction = TopBarNavigationAction.NavigationBack(
                            activity = activity,
                            navigator = navigator,
                        )
                    ),
                )
            )
        }

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    ProfileEffect.UserNotFound -> {
                        // TODO show confirm dialog
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_login_to_continue),
                            Toast.LENGTH_LONG
                        ).show()
                        navigator.noDebounce.replaceAll(WelcomeScreen())
                    }
                }
            }
        }

        ProfileContent(state = state)
    }
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier, state: ProfileState) {
    Scaffold(
        topBar = {
            TopBar(state.topBarState)
        }
    ) { innerPaddings ->
        val animatedTopPadding by animateDpAsState(
            targetValue = if (state.topBarState is TopBarState.None) 0.dp else innerPaddings.calculateTopPadding(),
            animationSpec = tween(UiConfig.NAVIGATION_ANIM_DURATION), label = ""
        )

        Box(
            modifier = Modifier
                .padding(top = animatedTopPadding)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.userPhoto)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.description_user_avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                    )
                }
                Text(
                    text = "${state.userDisplayName}",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "${state.userEmail}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun ProfileScreenPreview(modifier: Modifier = Modifier) {
    val previewHandler = AsyncImagePreviewHandler {
        FakeImage(color = Color.Gray.toArgb())
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ProfileContent(state = DebugProfileState)
    }
}