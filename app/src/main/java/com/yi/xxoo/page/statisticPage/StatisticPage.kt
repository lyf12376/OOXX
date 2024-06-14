package com.yi.xxoo.page.statisticPage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.page.loginPage.noRippleClickable
import com.yi.xxoo.utils.GifImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun StatisticPage(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            painterResource(id = R.drawable.statistic_bkg),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                text = "数据统计", modifier = Modifier
                    .padding(18.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp
            )
        }
        RollPage()


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun RollPage() {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        4
    }
    val coroutineScope = rememberCoroutineScope()

    val time = remember { mutableStateOf(0) } // 使用 State 来持有时间
    val isStarted = remember {
        mutableStateOf(true)
    }
    val isScroll = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isStarted.value) {
        isScroll.value = false
        time.value = 0
        Log.d("Tdsdasdasdasda","${isScroll.value}")
        while (time.value <= 3) {
            Log.d("TAG", "PassNumStatistic: $time")
            time.value++ // 每次循环递增时间
            delay(1000) // 延迟一秒
        }
        isScroll.value = true
    }


    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false
    ) { page ->
        when (page) {
            0 -> StartPage {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

            1 -> GameTimeStatistic {
                isStarted.value = false
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

            2 -> PassNumStatistic(isScroll){
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

            3 -> PlayTimesStatistic()
        }
    }


}

@Composable
fun StartPage(unit: () -> Unit) {

    Box(Modifier.fillMaxSize()) {
        GifImage(modifier = Modifier.fillMaxSize(), imageId = R.drawable.ooxx)
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 36.dp)
            .height(128.dp)
            .noRippleClickable {
                // 点击解锁
                unit()
            }) {
            Text(
                text = "点击解锁你的\n游戏数据总结吧",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                modifier = Modifier.align(Alignment.BottomCenter)
            )

        }

    }


}

@Composable
fun PassNumStatistic(isScroll: MutableState<Boolean>,unit: () -> Unit) {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedValue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            // Tween动画规范，这里指定总时长为1000毫秒
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            // 重复模式，这里使用`RepeatMode.Reverse`反向重复来实现往返动画
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // 将Float值转换为Dp值，这里使用Lerp(线性插值)在0.dp和72.dp之间插值
    val animatedDp = lerp(start = 0.dp, stop = 36.dp, fraction = animatedValue.value)



    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            GifImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                imageId = R.drawable.statistic_win
            )
            Text(
                text = "关关难过关关过", fontSize = 24.sp, modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "你已经通过了${UserData.passNum}关", fontSize = 24.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        }
        AnimatedVisibility(
            visible = isScroll.value,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "翻页",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(72.dp)
                        .weight(1f)
                        .offset(y = animatedDp)
                        .noRippleClickable { unit() }
                )
            }

        }
    }


}

@Composable
fun PlayTimesStatistic() {


    Column {
        GifImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            imageId = R.drawable.statistic_fight
        )
        Text(
            text = "你不相信天赋，只相信不断地努力", fontSize = 24.sp, modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "你已经开始了${UserData.gameTimes}次游戏",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

    }


}

@Composable
fun GameTimeStatistic(unit: () -> Unit) {

    var time1 by remember { mutableIntStateOf(0) } // 使用 State 来持有时间
    val isScroll1 = remember {
        mutableStateOf(false)
    }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedValue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            // Tween动画规范，这里指定总时长为1000毫秒
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            // 重复模式，这里使用`RepeatMode.Reverse`反向重复来实现往返动画
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // 将Float值转换为Dp值，这里使用Lerp(线性插值)在0.dp和72.dp之间插值
    val animatedDp = lerp(start = 0.dp, stop = 36.dp, fraction = animatedValue.value)

    // LaunchedEffect 用于启动协程，true作为key，表示这个效果在LaunchedEffect的参数不变时只执行一次
    LaunchedEffect(true) {
        try {
            while (time1 <= 3) {
                Log.d("TAG", "GameTimeStatistic: $time1")
                time1++ // 每次循环递增时间
                delay(1000) // 延迟一秒
            }
            isScroll1.value = true
        } catch (e: Exception) {
            Log.e("TAG", "Exception in coroutine: ", e)
        }
        Log.d("TAG", "GameTimeStatistic: ${isScroll1.value}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            GifImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                imageId = R.drawable.game_time
            )
            Text(
                text = "从你注册账号开始\n你已经在游戏中度过了${UserData.time}秒",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        AnimatedVisibility(
            visible = isScroll1.value,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "翻页",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(72.dp)
                        .weight(1f)
                        .offset(y = animatedDp)
                        .noRippleClickable {
                            unit()
                        }
                )
            }

        }
    }


}


