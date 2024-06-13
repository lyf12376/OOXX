package com.yi.xxoo.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.yi.xxoo.Const.ScreenData

@Composable
fun getScreenData()
{
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    ScreenData.screenWidthDp = screenWidthDp
    ScreenData.screenHeightDp = screenHeightDp
}