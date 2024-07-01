package com.yi.xxoo.page.rulePgae

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yi.xxoo.R

@Composable
fun RulePage(navController: NavController)
{
    Column (
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()){
        Row(modifier = Modifier
            .fillMaxWidth()
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
                text = "历史记录", modifier = Modifier
                    .padding(18.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp
            )
        }
        Column (Modifier.padding(16.dp)){
            Text(
                text = "Classic OOXX", fontSize = 36.sp,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "   每道谜题都由一个网格组成，网格中的不同地方放置了一些X和O。游戏的目的是将X和O填充到剩余的方格内:\n" +
                        "每一行以及每一列中没有超过俩个连续的X或O。\n" +
                        "每一行以及每一列中的X和O数量相同。\n" +
                        "每一行以及每一列都是唯一的。",
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace
            )
        }
        Image(painterResource(id = R.drawable.img_2), contentDescription = "",Modifier.size(300.dp).align(Alignment.CenterHorizontally))

    }
}