package com.yi.xxoo.page.matchPage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.github.compose.waveloading.DrawType
import com.github.compose.waveloading.WaveLoading
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.utils.GifImage
import com.yi.xxoo.utils.RippleButton
import com.yi.xxoo.utils.RippleButton.clearRipples
import com.yi.xxoo.utils.RippleButton.emitPress
import com.yi.xxoo.utils.RippleButton.emitRelease
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MatchPage(navController: NavController, matchViewModel: MatchViewModel = hiltViewModel()) {
    val matchResponse by matchViewModel.message.collectAsState()
    val matchSuccess = matchViewModel.matched.collectAsState()
    val startGame = matchViewModel.startGame.collectAsState()
    val isUserAccept = matchViewModel.accept.collectAsState()
    val progress = remember {
        Animatable(1f)
    }
    LaunchedEffect (startGame.value){
        if (startGame.value) {
            Log.d("TAG", "MatchPage: 13213")
            navController.navigate("PreparePage"){
                popUpTo("MatchPage")
            }
        }
    }

    LaunchedEffect(matchResponse) {
        if (matchResponse.code == 200) {
            Log.d("dasdasdasdasdasdsadasdasdasdsdasdas", "MatchPage: ")
            matchViewModel.matchSuccess(matchResponse.data)
            matchViewModel.connectSocket()
            Log.d("TAG", "MatchPage: ${matchResponse.data}")
        }

    }

    val scope = rememberCoroutineScope()
    var animationJob: Job? by remember { mutableStateOf(null) }

    LaunchedEffect(isUserAccept.value) {
        if (isUserAccept.value) {
            matchViewModel.stopTimer()
            Log.d("TAG", "MatchPage: 1324564654564564156")
            animationJob = scope.launch {
                progress.snapTo(1f)
                progress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 10000
                    )
                )
            }
        } else {
            animationJob?.cancel()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color("#EFEFEF".toColorInt()))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .statusBarsPadding()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }, modifier = Modifier
                        .padding(18.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "退出",
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
                Text(
                    text = "排位", modifier = Modifier
                        .padding(18.dp)
                        .align(Alignment.CenterVertically), fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp), contentAlignment = Alignment.Center
            ) {
                UserContent(UserData.name,UserData.userRank,UserData.photo)
            }
            Spacer(modifier = Modifier.height(36.dp))

            RepeatingRippleButton(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )

            TimerScreen(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }


    //接受匹配动画
    Box {
        AnimatedVisibility(visible = matchSuccess.value) {
            Column {
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.3f))
                    ) {
                        WaveLoading(
                            modifier = Modifier
                                .align(Alignment.Center),
                            backDrawType = DrawType.DrawImage,
                            foreDrawType = DrawType.DrawImage,
                            amplitude = 0.05f,
                            velocity = 0.05f,
                            progress = progress.value
                        ) {
                            Image(
                                painterResource(id = R.drawable.accept),
                                contentDescription = "",
                                modifier =
                                Modifier
                                    .align(Alignment.Center)
                                    .size(400.dp)
                            )
                        }
                        Box (modifier = Modifier.align(Alignment.Center)){
                            Text(
                                text = "接受匹配",
                                fontSize = 36.sp,
                                modifier = Modifier.clickable {
                                    matchViewModel.acceptedMatch()
                                }
                            )
                        }
                    }

                }

                Button(onClick = { matchViewModel.rejectMatch() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "拒绝匹配",fontSize = 36.sp,modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically))
                }
            }

        }
    }


}

@Composable
fun UserContent(name:String,rank:Int,photo:String) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // 使用maxWidth和maxHeight获取外部Box的尺寸
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        // 这里以Box填充为例，实际使用时根据需求调整
        Box(
            modifier = Modifier
                .fillMaxSize(), // 它现在会填满外层 BoxWithConstraints
            contentAlignment = Alignment.Center
        ) {
            if (rank == 0 || rank == 1) {
                Image(
                    painterResource(id = if (rank == 0) R.drawable.bronze else R.drawable.silver),
                    contentDescription = "边框",
                    // 根据外层Box大小动态调整尺寸
                    modifier = Modifier.size(maxWidth, maxHeight) // 根据外部尺寸的一定比例来决定
                )
            }else
                GifImage(modifier = Modifier.size(maxWidth, maxHeight),imageId = R.drawable.gold)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight), // 可以根据maxHeight调整高度
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(model = photo, contentDescription = "头像",modifier = Modifier
                .padding(top = maxHeight * 0.2f)
                .size(maxHeight * 0.3f)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(maxHeight * 0.15f))
            Text(
                text = name, fontSize = 48.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun RepeatingRippleButton(modifier: Modifier, matchViewModel: MatchViewModel = hiltViewModel()) {
    val interactionSource = remember { MutableInteractionSource() }

    val isRunning by matchViewModel.isRunning.collectAsState()
    var isRippleActive by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        if (!isRunning) {
            interactionSource.clearRipples()
        }
        while (isRunning) {
            interactionSource.emitPress()
            delay(500) // 控制波纹效果的频率
            interactionSource.emitRelease()
            delay(500)
        }
    }

    CompositionLocalProvider(LocalRippleTheme provides RippleButton.CustomRippleTheme) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(24.dp)) // 使用 dp 而不是 float
                .background(Color("#99EBFF".toColorInt()))
                .clickable {
                    if (isRunning) {
                        matchViewModel.cancel()
                        matchViewModel.resetTimer()
                    } else {
                        matchViewModel.matching()
                        matchViewModel.startTimer()
                    }
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = true)
                )
        ) {
            Text(
                text = "匹配",
                fontSize = 36.sp,
                modifier = Modifier.align(Alignment.Center),
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun TimerScreen(modifier: Modifier, matchViewModel: MatchViewModel = hiltViewModel()) {
    val time by matchViewModel.time.collectAsState()
    val isRunning by matchViewModel.isRunning.collectAsState()

    AnimatedVisibility(visible = isRunning) {
        Row(modifier) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "正在匹配：$time",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 24.sp
                )
            }
        }
    }

}




