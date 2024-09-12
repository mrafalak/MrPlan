package com.mr.presentation.home.training

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mr.presentation.R
import com.mr.presentation.home.base.HomeNavigator
import com.mr.domain.model.HomeTabEnum

class TrainingTab(
    private val deepLinkPath: String? = null
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.nav_label_training)
            val icon = painterResource(R.drawable.ic_training_24)

            return remember {
                TabOptions(
                    index = HomeTabEnum.TRAINING.index,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HomeNavigator(initialScreen = TrainingListScreen())
    }
}