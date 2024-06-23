package com.yi.xxoo.page.levelPage

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.utils.GameUtils
import kotlinx.coroutines.launch

@Composable
fun LevelPage(navController: NavController) {
    Column (modifier = Modifier.fillMaxSize()){
        LevelSelectionScreen(navController)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LevelSelectionScreen(navController: NavController,levelViewModel: LevelViewModel = hiltViewModel()) {
    val games = levelViewModel.gameList.collectAsState(initial = listOf())

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        games.value.size
    }
    val coins = levelViewModel.coin.collectAsState()
    val passNum = levelViewModel.passNum.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog)
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("解锁") },
            text = { Text("是否花20金币解锁该关卡") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    levelViewModel.unLockGame(coins.value - 20)
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false}) {
                    Text(text = "取消")
                }
            }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Gold box on the top right
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Row {
                Icon(painterResource(id = R.drawable.coin), contentDescription = "金币数量", modifier = Modifier.size(48.dp) ,tint = Color.Unspecified)
                Text(
                    text = "Coins: ${coins.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }

        Text(text = "第${pagerState.currentPage+1}关", fontSize = 48.sp, fontWeight = FontWeight.Bold)

        // Level selection in the middle with Pager
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous Level",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage - 1
                            )
                        }
                    }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .size(300.dp)
                    .border(3.dp, Color("#9F20B5".toColorInt()))
            ) { page ->
                if (page <= passNum.value)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        GamePreview(init = games.value[page])
                    }
                else if (page == passNum.value + 1)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column (Modifier.clickable {
                            showDialog = true
                        }){
                            Image(painterResource(id = R.drawable.locked), contentDescription = "未解锁", modifier = Modifier.padding(16.dp))
                            Text(text = "未解锁", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            ))
                        }

                    }
                else
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Image(painterResource(id = R.drawable.locked), contentDescription = "未解锁", modifier = Modifier.padding(16.dp))
                            Text(text = "未解锁", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            ))
                        }

                    }

            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next Level",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    }
            )
        }

        Image(
            painterResource(id = R.drawable.play),
            contentDescription = "开始游戏",
            modifier = Modifier
                .size(80.dp)
                .clickable {
                    navController.navigate("OfflineGamePage/${pagerState.currentPage + 1}")
                })

        // Record at the bottom
//        Text(
//            text = "世界排行",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(16.dp)
//        )
//        Image(painterResource(id = R.drawable.wb), contentDescription = "世界排行", modifier = Modifier.size(80.dp))
        LevelPageNavigation{
            navController.navigate("MinePage")
        }
    }
}

@Composable
fun LevelPageNavigation(unit:()->Unit)
{
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
        Column {
            Icon(
                painterResource(id = R.drawable.game_selected),
                contentDescription = "Previous Level",
                modifier = Modifier
                    .size(48.dp),
                tint = Color.Unspecified
            )
            Text(text = "游戏", fontSize = 20.sp)
        }
        Column {
            Icon(
                painterResource(id = R.drawable.mine),
                contentDescription = "我的",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        unit()
                    },
                tint = Color.Unspecified
            )
            Text(text = "我的", fontSize = 20.sp)
        }
    }
}



@Composable
fun GamePreview(init: String) {
    val init = GameUtils.expandStringToNxNList(init)
    val gridSize = init.size // 从init确定的网格尺寸


    // 从游戏初始化状态计算行和列的计数
    val rowCountX = init.map { row -> row.count { it == 'X' } }.toIntArray()
    val rowCountO = init.map { row -> row.count { it == 'O' } }.toIntArray()
    val colCountX = IntArray(gridSize) { col -> init.count { it[col] == 'X'} }
    val colCountO = IntArray(gridSize) { col -> init.count { it[col] == 'O'} }



    val paddingValues = PaddingValues(end = 2.dp, bottom = 2.dp)
    val recSize = ((300 - 16) - (gridSize) * 2) / (gridSize + 1) // 每个单元格的尺寸

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize + 1), // 添加额外的一列用于显示计数
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val cellSize = size.width / (gridSize + 1)
                for (i in 0 until gridSize + 2) {
                    // 画竖线
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = cellSize * i, y = 0f),
                        end = Offset(x = cellSize * i, y = cellSize * (gridSize + 1)),
                        strokeWidth = 3f
                    )
                    // 画横线
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = 0f, y = cellSize * i),
                        end = Offset(x = size.width, y = cellSize * i),
                        strokeWidth = 3f
                    )
                }
            }
    ) {
        items((gridSize + 1) * (gridSize + 1)) { index ->
            Log.d("TAG", "GameGrid: $index")
            val row = index / (gridSize + 1)
            val col = index % (gridSize + 1)

            // 外侧的行和列用来展示计数
            if (row == 0 && col == 0){
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .size(recSize.dp)
                ) {
                    Row (modifier = Modifier.fillMaxHeight()){
                        Text(
                            text = "X",
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Top),
                            color = Color.Green
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "O",
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Bottom),
                            color = Color.Red
                        )
                    }

                }
            }
            else if (row == 0 || col == 0 ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .size(recSize.dp)
                ) {
                    val countX = if (col != 0) colCountX[col - 1] else rowCountX[row - 1]
                    val countO = if (col != 0) colCountO[col - 1] else rowCountO[row - 1]
                    Row (modifier = Modifier.fillMaxHeight()){
                        Text(
                            text = "$countX",
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Top),
                            color = Color.Green
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$countO",
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Bottom),
                            color = Color.Red,
                        )
                    }

                }
            } else {
                // 网格内的单元格用来展示游戏状态
                val cellValue = init[row-1][col-1]
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .size(recSize.dp)
                ) {
                    if (cellValue == 'X')
                        Text(
                            text = cellValue.toString(),
                            fontSize = 24.sp,
                            color = Color.Green
                        )
                    else if (cellValue == 'O')
                        Text(
                            text = cellValue.toString(),
                            fontSize = 24.sp,
                            color = Color.Red
                        )
                    else {
                        // 空白单元格
                    }
                }

            }
        }
    }
}
