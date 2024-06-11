package com.yi.xxoo.page.statisticPage

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yi.xxoo.R
import com.yi.xxoo.page.loginPage.noRippleClickable
import com.yi.xxoo.utils.GifImage
import kotlinx.coroutines.launch

@Composable
@Preview
fun StatisticPage() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)) {
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
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "退出",
                modifier = Modifier
                    .padding(18.dp)
                    .size(36.dp)
                    .clickable {
                        // 返回上一页
                    }
            )
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
        5
    }
    val coroutineScope = rememberCoroutineScope()


    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false
    ) { page ->
        when (page) {
            0 -> StartPage {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage+1)
                }
            }
            1 -> GameTimeStatistic {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage+1)
                }
            }
            2 -> PassNumStatistic {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage+1)
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
fun PassNumStatistic(unit: () -> Unit) {
    Column {
        GifImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            imageId = R.drawable.statistic_win
        )
        Text(text = "关关难过关关过", fontSize = 24.sp, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally))
        Text(text = "你已经通关了10次", fontSize = 24.sp, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .noRippleClickable {
                unit()
            })
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
        Text(text = "你不相信天赋，只相信不断地努力", fontSize = 24.sp, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)
        )
        Text(text = "你已经开始了3次游戏", fontSize = 24.sp, modifier = Modifier
            .align(Alignment.CenterHorizontally))
    }
}

@Composable
fun GameTimeStatistic(unit: () -> Unit) {

    Column {
        GifImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            imageId = R.drawable.game_time
        )
        Text(text = "从你注册账号开始\n你已经在游戏中度过了一个小时", fontSize = 24.sp, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally)
            .noRippleClickable { unit() }
        )
    }


}


