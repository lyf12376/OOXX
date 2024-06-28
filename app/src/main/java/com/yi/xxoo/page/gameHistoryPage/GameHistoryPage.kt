package com.yi.xxoo.page.gameHistoryPage

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.Room.history.GameHistory

@Composable
fun GameHistoryPage(navController: NavController, gameHistoryViewModel: GameHistoryViewModel = hiltViewModel()) {
    val gameHistory = gameHistoryViewModel.gameHistory.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()) {
        Row(modifier = Modifier.fillMaxWidth()) {
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
                text = "历史记录", modifier = Modifier
                    .padding(18.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp
            )
        }
        LazyColumn (modifier = Modifier
            .weight(1f)
            .fillMaxWidth()){
            items(gameHistory.value.size) {
                HistoryItem(gameHistory.value[it])
                Divider()
            }
        }
    }
}

@Composable
fun HistoryItem(gameHistory: GameHistory)
{
    Row (Modifier.fillMaxWidth()){
        Text(text = gameHistory.startTime,modifier = Modifier
            .padding(8.dp)
            .align(Alignment.CenterVertically), fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .padding(8.dp)
            .background(if (gameHistory.state == 1) Color.Green else Color.Red, RoundedCornerShape(8.dp))
            ){
            Text(text = if (gameHistory.state == 1)"胜利" else "失败",modifier = Modifier.padding(8.dp), fontSize = 18.sp)
        }
    }

}