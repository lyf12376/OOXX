package com.yi.xxoo.page.settlePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yi.xxoo.R

@Composable
@Preview
fun SettlePage()
{
    Column {
        gameSettlement()
        Divider()
        gameSettlement()
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
@Preview
fun UserContent(name:String = "name") {
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
            Image(
                painterResource(id = R.drawable.bronze),
                contentDescription = "青铜边框",
                // 根据外层Box大小动态调整尺寸
                modifier = Modifier.size(maxWidth, maxHeight) // 根据外部尺寸的一定比例来决定
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight), // 可以根据maxHeight调整高度
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(id = R.drawable.rank),
                contentDescription = "头像",
                modifier = Modifier
                    .padding(top = maxHeight * 0.15f)
                    .size(maxHeight * 0.2f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(maxHeight * 0.05f))
            Text(
                text = name, fontSize = 48.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(maxHeight * 0.05f))
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
}