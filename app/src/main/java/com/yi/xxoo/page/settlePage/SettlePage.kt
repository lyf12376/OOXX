package com.yi.xxoo.page.settlePage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.github.compose.waveloading.DrawType
import com.github.compose.waveloading.WaveLoading
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.Settlement
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.page.preparePage.AnimatedComponentLeft
import com.yi.xxoo.page.preparePage.AnimatedComponentRight
import com.yi.xxoo.utils.GifImage

@Composable
fun SettlePage(navController: NavController,settleViewModel: SettleViewModel = hiltViewModel())
{
    val isGifShow = settleViewModel.gifShow.collectAsState()
    val isFirstFlagShow = settleViewModel.firstFlagShow.collectAsState()
    val isNextFlagShow = settleViewModel.nextFlagShow.collectAsState()
    val exit = settleViewModel.exit.collectAsState()
    settleViewModel.startTimer()

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
            GifImage(modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 32.dp), imageId = R.drawable.game_over)
        }
    }
    AnimatedVisibility(visible = isFirstFlagShow.value) {
        AnimatedComponentLeft {
            SettleContent(UserData.name,Settlement.userPassTime,Settlement.userSubmitTimes,UserData.photo,UserData.userRank)
        }
    }
    AnimatedVisibility(visible = isNextFlagShow.value) {
        AnimatedComponentRight {
            SettleContent(OnlineGame.enemyName,Settlement.enemyPassTime,Settlement.enemySubmitTimes,OnlineGame.enemyPhoto,OnlineGame.enemyRank)
        }
    }

    AnimatedVisibility(visible = exit.value) {
        Box(modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()) {
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)) {
                Text(text = "返回大厅", fontSize = 24.sp)
            }
        }
    }

}

@Composable
@Preview
fun gameSettlement()
{
    Column (modifier = Modifier.fillMaxWidth()){
        Row {
            Image(
                painterResource(id = R.drawable.coin),
                contentDescription = "头像",
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(text = "name", modifier = Modifier.align(Alignment.CenterVertically), fontSize = 32.sp)
        }
        Row {
            Text(text = "过关时间:", modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically), fontSize = 24.sp)
        }
        Row {
            Text(text = "提交次数:", modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically), fontSize = 24.sp)
        }
        Row {
            Text(text = "总时间:", modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically), fontSize = 24.sp)
        }
    }
}

@Composable
fun SettleContent(name:String,passTime:Int,submitTimes:Int,photo:String,rank:Int){
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
            Spacer(modifier = Modifier.height(maxHeight * 0.05f))
            Text(
                text = name, fontSize = 48.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(maxHeight * 0.05f))
            Row {
                Text(text = "过关时间: $passTime", modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp)
            }
            Row {
                Text(text = "提交次数: $submitTimes", modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp)
            }
            Row {
                Text(text = "总时间: ${passTime+submitTimes * 5 - 5}", modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp)
            }
        }
    }
}