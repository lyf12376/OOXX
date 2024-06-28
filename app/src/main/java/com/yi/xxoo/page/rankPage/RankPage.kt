package com.yi.xxoo.page.rankPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankPage(navController: NavController) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }, // 总页数
        initialPage = 0, // 初始页面索引
    )

    // 排行榜标题
    val tabTitles = listOf("通关数", "游戏时间")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()) {
        Box {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.rank_bkg),
                    contentDescription = "排行榜背景",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .statusBarsPadding()
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() }, modifier = Modifier
                            .padding(9.dp)
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
                            .padding(9.dp)
                            .align(Alignment.CenterVertically), fontSize = 24.sp
                    )
                }
            }
        }


        // 底边栏
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        // 更新选中的页签索引和Pager状态
                        selectedTabIndex = index
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }
                )
            }
        }

        // 绑定HorizontalPager与底边栏
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(10.dp),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> passNumRankingList(pageTitle = "通关数")
                1 -> gameTimeRankingList(pageTitle = "游戏时间")
            }
        }

        // 保持底边栏和Pager页同步
        LaunchedEffect(pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }
    }
}

@Composable
fun passNumRankingList(pageTitle: String = "", rankViewModel: RankViewModel = hiltViewModel()) {
    val passNumList = rankViewModel.passNumRankList.collectAsState()
    LazyColumn {
        items(passNumList.value.size) { index ->
            ListItem(index,name = passNumList.value[index].userName,passNum = passNumList.value[index].passNum, whichRank = 1)
            Divider()
        }
    }
}

@Composable
fun gameTimeRankingList(pageTitle: String = "", rankViewModel: RankViewModel = hiltViewModel()) {
    val gameTimeList = rankViewModel.gameTimeRankList.collectAsState()
    LazyColumn {
        items(gameTimeList.value.size) { index ->
            ListItem(index,name = gameTimeList.value[index].userName,gameTime = gameTimeList.value[index].gameTime, whichRank = 2)
            Divider()
        }
    }
}

@Composable
fun ListItem(rank: Int,name: String,passNum: Int = 0,gameTime: Int = 0,whichRank: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "${rank + 1}", fontSize = 24.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 18.dp)
                .width(32.dp)
        )
        Text(
            text = name, fontSize = 24.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
                .weight(1f)
        )
        Text(
            text = if (whichRank == 1) "$passNum" else "$gameTime", fontSize = 24.sp, modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 32.dp)
        )
    }
}
