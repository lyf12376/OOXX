package com.yi.xxoo.page.preparePage

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.compose.waveloading.DrawType
import com.github.compose.waveloading.WaveLoading
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.navigation.Screen
import com.yi.xxoo.page.matchPage.UserContent
import com.yi.xxoo.utils.GifImage
import kotlinx.coroutines.delay

@Composable
fun PreparePage(navController: NavController,prepareViewModel: PrepareViewModel = hiltViewModel()) {
    val isGifShow = prepareViewModel.gifShow.collectAsState()
    val isFirstFlagShow = prepareViewModel.firstFlagShow.collectAsState()
    val isNextFlagShow = prepareViewModel.nextFlagShow.collectAsState()
    prepareViewModel.startTimer()
    val progress = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val isLoading = prepareViewModel.loading.collectAsState()
    LaunchedEffect (isLoading.value){
        if (isLoading.value){
            delay(5000)
            navController.navigate("OnlineGamePage")
        }
    }
    LaunchedEffect(isLoading.value) {
        if (isLoading.value)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 5000
                )
            )
    }


    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painterResource(id = R.drawable.prepare),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }


    AnimatedVisibility(visible = isGifShow.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            GifImage(modifier = Modifier.align(Alignment.Center), imageId = R.drawable.fight)
        }
    }
    AnimatedVisibility(visible = isFirstFlagShow.value) {
        AnimatedComponentLeft {
            UserContent(UserData.name)
        }
    }
    AnimatedVisibility(visible = isNextFlagShow.value) {
        AnimatedComponentRight {
            UserContent(OnlineGame.enemyName)
        }
    }
    AnimatedVisibility(visible = isLoading.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            WaveLoading(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                backDrawType = DrawType.DrawImage,
                foreDrawType = DrawType.DrawImage,
                amplitude = 0.05f,
                velocity = 0.2f,
                progress = progress.value
            ) {
                Image(painterResource(id = R.drawable.loading), contentDescription = "", modifier = Modifier
                    .fillMaxSize()
                    .align(
                        Alignment.BottomCenter
                    ))
            }
        }
    }


}

@Composable
fun AnimatedComponentLeft(flag: @Composable () -> Unit) {
    val animate = remember { mutableStateOf(true) }
    // 自动开始动画，而不是点击后
    LaunchedEffect(true) {
        delay(3000) // 延迟3秒
        animate.value = false // 触发动画
    }

    val scale = animateFloatAsState(targetValue = if (animate.value) 1f else 0.40f, label = "")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }

    // 计算组件缩小后的大小
    val targetWidthPx = screenWidthPx * scale.value
    val targetHeightPx = screenHeightPx * scale.value

    // 计算组件应移动的X和Y的距离
    val targetOffsetX = -(screenWidthPx - targetWidthPx) / 2 + 20
    val targetOffsetY = -(screenHeightPx - targetHeightPx) / 2 + 20

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                translationX = if (animate.value) 0f else targetOffsetX
                translationY = if (animate.value) 0f else targetOffsetY
            },
        contentAlignment = Alignment.Center
    ) {
        flag()
    }
}

@Composable
fun AnimatedComponentRight(flag: @Composable () -> Unit) {
    val animate = remember { mutableStateOf(true) }
    // 自动开始动画，而不是点击后
    LaunchedEffect(true) {
        delay(3000) // 延迟3秒
        animate.value = false // 触发动画
    }

    val scale = animateFloatAsState(targetValue = if (animate.value) 1f else 0.40f, label = "")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.toPx() }
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }

    // 计算组件缩小后的大小
    val targetWidthPx = screenWidthPx * scale.value
    val targetHeightPx = screenHeightPx * scale.value

    // 计算组件应移动的X和Y的距离
    val targetOffsetX = (screenWidthPx - targetWidthPx) / 2 - 20
    val targetOffsetY = -(screenHeightPx - targetHeightPx) / 2 + 20

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                translationX = if (animate.value) 0f else targetOffsetX
                translationY = if (animate.value) 0f else targetOffsetY
            },
        contentAlignment = Alignment.Center
    ) {
        flag()
    }
}

//@Preview
//@Composable
//fun pre()
//{
//
//        WaveLoading(
//            Modifier.size(300.dp),
//            backDrawType = DrawType.DrawImage,
//            foreDrawType = DrawType.DrawImage,
//            amplitude = 0.05f,
//            velocity = 0.2f,
//        ) {
//            Image(painterResource(id = R.drawable.win), contentDescription = "", modifier = Modifier.align(
//                Alignment.BottomCenter))
//        }
//
//}