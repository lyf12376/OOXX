package com.yi.xxoo.page.onlineGamePage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.ScreenData
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.utils.GameUtils
import kotlinx.coroutines.delay

@Composable
fun OnlineGamePage(navController: NavController,onlineGameViewModel: OnlineGamePageViewModel = hiltViewModel())
{
    val gameSuccess by onlineGameViewModel.gameSuccess.collectAsState()
    val context = LocalContext.current
    val musicId = R.raw.win

    var time by remember { mutableIntStateOf(0) } // 使用 State 来持有时间

    // LaunchedEffect 用于启动协程，true作为key，表示这个效果在LaunchedEffect的参数不变时只执行一次
    LaunchedEffect(true) {
        onlineGameViewModel.loadMusic(context, musicId)
        while (!gameSuccess) {
            time++ // 每次循环递增时间
            delay(1000) // 延迟一秒
        }
    }
    
    val init = GameUtils.expandStringToNxNList(OnlineGame.init)
    val now = remember { mutableStateOf(init.map { it.toMutableList() }.toMutableList()) }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("退出") },
            text = { Text("是否退出游戏") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    //退出游戏逻辑
                    onlineGameViewModel.exitGame()
                }) {
                    Text("确定")
                }
                Button(onClick = {
                    showDialog = false
                }) {
                    Text("取消")
                }
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "退出",
                modifier = Modifier
                    .padding(18.dp)
                    .size(36.dp)
                    .clickable {
                        showDialog = true
                    }
            )
            Timer(
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                time = time
            )
        }
        
        GameGrid(init,now)
        Button(
            onClick = {
                now.value = init.map { it.toMutableList() }.toMutableList()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "重置" )
        }
        Button(
            onClick = {
                onlineGameViewModel.check(now.value.joinToString("") { it.joinToString("") },time)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "提交")

        }

    }

    Box {
        com.yi.xxoo.page.offlineGamePage.GameSuccessAnimation(modifier = Modifier
            .fillMaxSize()
            .clickable {

            }, 
            visible = gameSuccess,
            playMusic = {
                onlineGameViewModel.playMusic()
            }) {
            onlineGameViewModel.releaseMusic()
        }
    }

}

@Composable
fun GameGrid(init:List<List<Char>>, now: MutableState<MutableList<MutableList<Char>>>) {
    val gridSize = init.size // 从init确定的网格尺寸
    val recSize = ((ScreenData.screenWidthDp.value - 16) - (gridSize) * 2) / (gridSize + 1) // 每个单元格的尺寸

    // 从游戏初始化状态计算行和列的计数
    val rowCountX by remember { derivedStateOf { now.value.map { row -> row.count { it == 'X' } }.toIntArray() } }
    val rowCountO by remember { derivedStateOf { now.value.map { row -> row.count { it == 'O' } }.toIntArray() } }
    val colCountX by remember { derivedStateOf { IntArray(gridSize) { col -> now.value.count { it[col] == 'X'} } } }
    val colCountO by remember { derivedStateOf { IntArray(gridSize) { col -> now.value.count { it[col] == 'O'} } } }


    val paddingValues = PaddingValues(end = 2.dp, bottom = 2.dp)

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize + 1), // 添加额外的一列用于显示计数
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                        .size(recSize.dp)
                        .padding(paddingValues)
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
                        .size(recSize.dp)
                        .padding(paddingValues)
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
                val cellValue = now.value[row-1][col-1]
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(recSize.dp)
                        .padding(paddingValues)
                        .clickable {
                            // 创建now列表的副本并修改
                            val newNow = now.value
                                .map { it.toMutableList() }
                                .toMutableList()
                            newNow[row - 1][col - 1] =
                                if (cellValue == 'X') 'O' else if (cellValue == 'O') 'X' else 'X'

                            // 更新状态
                            now.value = newNow
                        }
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

@Composable
fun Timer(modifier: Modifier, time:Int) {

    // 显示时间的Text
    Box(modifier = modifier) {
        Text(
            text = "Time: $time", fontSize = 24.sp, modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp)
        )
    }
}

@Composable
fun Record(level: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = "WB: Fast",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(0.5f)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text =
                if (UserData.bestRecord.size < level) "PB: 等你创造记录"
                else "PB: ${UserData.bestRecord[level-1]}",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(0.5f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GameSuccessAnimation(modifier: Modifier, visible: Boolean, playMusic:()->Unit = {}, release:()->Unit = {}) {
    val scale = remember {
        Animatable(1f)
    }
    // 对visible进行监听，如果变为true，则执行动画
    LaunchedEffect(visible) {
        if (visible) {
            playMusic()
            scale.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 0
                )
            )
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(), // 进入时的动画，此处可以自定义
        exit = fadeOut() // 退出时的动画，此处可以自定义
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize() // 覆盖整个父容器
                .background(Color.Gray.copy(alpha = 0.5f)) // 半透明灰色滤镜效果
        )
        Image(
            painter = painterResource(id = R.drawable.win),
            contentDescription = "胜利",
            modifier = modifier
                .fillMaxSize()
                .scale(scale.value) // 应用缩放动画
        )
    }
}