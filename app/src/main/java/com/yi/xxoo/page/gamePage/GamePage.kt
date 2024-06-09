package com.yi.xxoo.page.gamePage

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.utils.GameUtils
import kotlinx.coroutines.delay

@Composable
@Preview
fun GamePage() {
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
                    }
            )
            Timer(
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "第X关",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(bottom = 0.dp)
                        .alpha(0.5f)
                        .align(Alignment.CenterHorizontally)
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(text = "难度:", fontSize = 16.sp)
                    for (i in 1..5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "星星",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
        Record()
        GameGrid()
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "重置" )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "提交")

        }

    }
}

@Composable
fun GameGrid() {
    val game = Game(0, 5, "xoxoxoxox", "xoxoxoxox")
    val init = GameUtils.expandStringToNxNList(game.init)
    val gridSize = init.size // 从init确定的网格尺寸

    // 从游戏初始化状态计算行和列的计数
    val rowCountX = IntArray(gridSize) { row ->
        init[row].count { it == 'x' }
    }
    val rowCountO = IntArray(gridSize) { row ->
        init[row].count { it == 'o' }
    }
    val colCountX = IntArray(gridSize) { col ->
        init.count { it[col] == 'x'}
    }
    val colCountO = IntArray(gridSize) { col ->
        init.count { it[col] == 'o' }
    }
    
    val paddingValues = PaddingValues(end = 2.dp, bottom = 2.dp)

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize + 1), // 添加额外的一列用于显示计数
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items((gridSize + 1) * (gridSize + 1)) { index ->
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
                    val countX = if (col != 0) rowCountX[col - 1] else if (row != 0) colCountX[row - 1] else 0
                    val countO = if (col != 0) rowCountO[col - 1] else if (row != 0) colCountO[row - 1] else 0
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
                        .size(50.dp)
                        .background(Color.LightGray)
                        .clickable {
                            // 当单元格被点击时，切换状态
                            // 更新状态和计数
                        }
                ) {
                    if (cellValue == 'x')
                        Text(
                            text = cellValue.toString(),
                            fontSize = 24.sp,
                            color = Color.Green
                        )
                    else if (cellValue == 'o')
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
fun Timer(modifier: Modifier) {
    var time by remember { mutableIntStateOf(0) } // 使用 State 来持有时间

    // LaunchedEffect 用于启动协程，true作为key，表示这个效果在LaunchedEffect的参数不变时只执行一次
    LaunchedEffect(true) {
        while (true) {
            time++ // 每次循环递增时间
            delay(1000) // 延迟一秒
        }
    }

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
fun Record() {
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
                text = "PB: Fast", fontSize = 20.sp, modifier = Modifier
                    .padding(16.dp)
                    .alpha(0.5f)
                    .align(Alignment.Center)
            )
        }
    }
}

