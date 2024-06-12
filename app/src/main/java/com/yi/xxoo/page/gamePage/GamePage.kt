package com.yi.xxoo.page.gamePage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.utils.GameUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GamePage(navController: NavController,level:Int,gameViewModel: GameViewModel = hiltViewModel()) {
    val gameDetail by gameViewModel.gameDetail.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val gameSuccess by gameViewModel.gameSuccess.collectAsState()

    // 调用getGame获取指定等级的游戏信息
    LaunchedEffect(level) {
        gameViewModel.getGame(level)
    }
    gameViewModel.target = gameDetail?.target ?: ""

    var time by remember { mutableIntStateOf(0) } // 使用 State 来持有时间

    // LaunchedEffect 用于启动协程，true作为key，表示这个效果在LaunchedEffect的参数不变时只执行一次
    LaunchedEffect(true) {
        while (!gameSuccess) {
            time++ // 每次循环递增时间
            delay(1000) // 延迟一秒
        }

    }



    val game = gameDetail ?: return
    val init = GameUtils.expandStringToNxNList(game.init)
    val now = remember { mutableStateOf(init.map { it.toMutableList() }.toMutableList()) }


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
                        // 返回上一页
                        navController.popBackStack()
                    }
            )
            Timer(
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                time = time
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "第${level}关",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(bottom = 0.dp)
                        .alpha(0.5f)
                        .align(Alignment.CenterHorizontally)
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "难度:", fontSize = 16.sp)
                    gameDetail?.let {
                        for (i in 1..it.difficulty) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "星星",
                                tint = Color.Red
                            )
                        }
                    }

                }
            }
        }
        Record(level)
        GameGrid(gameDetail,now)
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
                gameViewModel.check(now.value.joinToString("") { it.joinToString("") },time,level)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "提交")

        }

    }
    Box {
        GameSuccessAnimation(modifier = Modifier
            .fillMaxSize()
            .clickable { navController.popBackStack() }, visible = gameSuccess)
    }
}

@Composable
fun GameGrid(game: Game?,now: MutableState<MutableList<MutableList<Char>>>) {
    val game = game ?: return
    val init = GameUtils.expandStringToNxNList(game.init)
    val gridSize = init.size // 从init确定的网格尺寸

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
                        .size(50.dp)
                        .background(Color.LightGray)
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
                        .size(50.dp)
                        .background(Color.LightGray)
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
                        .padding(paddingValues)
                        .size(50.dp)
                        .background(Color.LightGray)
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
                text = "PB: ${UserData.bestRecord[level-1]}", fontSize = 20.sp, modifier = Modifier
                    .padding(16.dp)
                    .alpha(0.5f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GameSuccessAnimation(modifier: Modifier,visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut()
    ) {
        // 这里是您的成功动画展示代码
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Success",
            modifier = modifier
                .padding(16.dp),
            tint = Color.Green
        )
        // 您可以根据需要替换成任何您喜欢的动画效果
    }
}

