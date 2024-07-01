package com.yi.xxoo.page.minePage

import android.util.Log
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.R
import com.yi.xxoo.navigation.Screen
import com.yi.xxoo.page.loginPage.noRippleClickable
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
fun MinePage(navController: NavController, mineViewModel: MineViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("无法查看") },
            text = { Text("你正处于离线模式请在线模式查看") },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("确定")
                }
            }
        )
    }
    LaunchedEffect(true) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF0F0F0))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .statusBarsPadding()
            ) {
                photo {
                    navController.navigate(Screen.DocumentPage.route)
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
                    if (GameMode.isNetworkEnabled)
                        navController.navigate("RankPage")
                    else {
                        showDialog.value = true
                    }
                }
                Divider()
                history {
                    navController.navigate("GameHistoryPage")
                }
                Divider()
                rule {
                    navController.navigate(Screen.RulePage.route)
                }
                Divider()
                logOut {
                    UserData.resetUserData()
                    navController.navigate("LoginPage") {
                        popUpTo("MinePage") {
                            inclusive = true
                        }
                    }
                }
            }
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        MinePageNavigation(
            Modifier
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            navController.navigate("LevelPage")
        }
    }
}

@Composable
fun photo(unit: () -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        if (UserData.photo != "") {
            AsyncImage(model = UserData.photo, contentDescription = "头像", modifier = Modifier
                .padding(MySize.photoPadding)
                .size(MySize.photoSize)
                .clickable { unit() }
            )
        } else {
            Image(painterResource(id = R.drawable.img),
                contentDescription = "默认头像",
                modifier = Modifier
                    .padding(MySize.photoPadding)
                    .size(MySize.photoSize)
                    .clickable { unit() })
        }

        Text(
            text = UserData.name, fontSize = MySize.nameSize, modifier = Modifier
                .padding(MySize.ItemPadding)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun achievement(unit: () -> Unit) {
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
fun statistics(unit: () -> Unit) {
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
fun rank(unit: () -> Unit) {
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
fun logOut(unit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.log_out),
            contentDescription = "退出登录",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "退出登录", fontSize = MySize.textSize, modifier = Modifier
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
fun history(unit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.history),
            contentDescription = "退出登录",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "历史记录", fontSize = MySize.textSize, modifier = Modifier
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
fun rule(unit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                unit()
            }
    ) {
        Image(
            painterResource(id = R.drawable.rule),
            contentDescription = "退出登录",
            modifier = Modifier
                .padding(MySize.ItemPadding)
                .size(MySize.iconSize)
        )
        Text(
            text = "游戏规则", fontSize = MySize.textSize, modifier = Modifier
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
fun MinePageNavigation(modifier: Modifier, unit: () -> Unit) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
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


