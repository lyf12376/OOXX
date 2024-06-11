package com.yi.xxoo.page.achievementPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
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
fun AchievementPage()
{
    Column (modifier = Modifier.fillMaxSize()){
        Row (modifier = Modifier.fillMaxWidth()){
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
            Text(text = "成就",modifier = Modifier
                .padding(18.dp)
                .align(Alignment.CenterVertically), fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)){
            AchievementItem(Modifier.weight(1f),R.drawable.time_achievement_gold, "时间刺客", "游玩时间超过十分钟")
        }
        Row (modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)){
            AchievementItem(Modifier.weight(1f),R.drawable.achievement_pass,"通关达人","通关三次游戏")
        }
        Row (modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)){
            AchievementItem(Modifier.weight(1f),R.drawable.achievement_first,"一举夺魁","在世界排名获得一次第一名")
        }


    }
}

@Composable
fun AchievementItem(modifier: Modifier, resourceId:Int,achievementName: String = "成就名称", achievementDescription: String = "成就描述")
{
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .padding(10.dp)
            .align(Alignment.Center)) {
            Icon(
                painterResource(id = resourceId),
                contentDescription = "成就图标",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = achievementName, fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = achievementDescription, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}