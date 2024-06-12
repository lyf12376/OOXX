package com.yi.xxoo.page.minePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.R
import kotlinx.coroutines.launch

object MySize {
    val photoSize = 72.dp
    val nameSize = 28.sp
    val iconSize = 48.dp
    val textSize = 20.sp
    val ItemPadding = 20.dp
    val photoPadding = 25.dp
}

@Composable
fun MinePage(navController: NavController,mineViewModel: MineViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        mineViewModel.getUserData()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF0F0F0))
                .fillMaxSize()
        ) {
            photo {

            }
            Spacer(modifier = Modifier.height(32.dp))
            achievement {
                navController.navigate("AchievementPage")
            }
            Divider()
            statistics {
                navController.navigate("StatisticPage")
            }
            Divider()
            rank {
                navController.navigate("RankPage")
            }
        }
        MinePageNavigation(
            Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)){
            navController.navigate("LevelPage")
        }
    }
}

@Composable
fun photo(unit:()->Unit) {
    Row (modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()) {
        Image(
            painterResource(id = R.drawable.coin), contentDescription = "头像", modifier = Modifier
                .padding(MySize.photoPadding)
                .size(MySize.photoSize)
        )
        Text(
            text = "name", fontSize = MySize.nameSize, modifier = Modifier
                .padding(MySize.ItemPadding)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun achievement(unit:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.achievement),
            contentDescription = "头像",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "成就", fontSize = MySize.textSize, modifier = Modifier
                .padding(MySize.ItemPadding)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "更多",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
                .align(Alignment.CenterVertically),
            tint = Color.Gray
        )
    }
}

@Composable
fun statistics(unit:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.statistic),
            contentDescription = "统计",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "统计", fontSize = MySize.textSize, modifier = Modifier
                .padding(MySize.ItemPadding)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "更多",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
                .align(Alignment.CenterVertically),
            tint = Color.Gray
        )
    }
}

@Composable
fun rank(unit:()->Unit)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.rank),
            contentDescription = "排行",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "排行", fontSize = MySize.textSize, modifier = Modifier
                .padding(MySize.ItemPadding)
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "更多",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
                .align(Alignment.CenterVertically),
            tint = Color.Gray
        )
    }

}
@Composable
fun MinePageNavigation(modifier: Modifier,unit:()->Unit)
{
    Row (modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
        Column {
            Icon(
                painterResource(id = R.drawable.game),
                contentDescription = "Previous Level",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        unit()
                    },
                tint = Color.Unspecified
            )
            Text(text = "游戏", fontSize = 20.sp)
        }
        Column {
            Icon(
                painterResource(id = R.drawable.mine_selected),
                contentDescription = "我的",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {

                    },
                tint = Color.Unspecified
            )
            Text(text = "我的", fontSize = 20.sp)
        }
    }
}


