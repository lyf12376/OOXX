package com.yi.xxoo.page.matchPage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.R
import com.yi.xxoo.utils.RippleButton
import com.yi.xxoo.utils.RippleButton.clearRipples
import com.yi.xxoo.utils.RippleButton.emitPress
import com.yi.xxoo.utils.RippleButton.emitRelease
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MatchPage(navController: NavController,matchViewModel: MatchViewModel = hiltViewModel()) {
    val matchResponse by matchViewModel.message.collectAsState()

    LaunchedEffect (matchResponse){
        if (matchResponse.code == 200)
        {
            Log.d("TAG", "MatchPage: ${matchResponse.data}")
            navController.navigate("")
        }

    }

    Column (Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
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
                text = "排位", modifier = Modifier
                    .padding(18.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp
            )
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(600.dp), contentAlignment = Alignment.Center){
            UserContent()
        }
        Spacer(modifier = Modifier.height(36.dp))

        RepeatingRippleButton(
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .align(Alignment.CenterHorizontally)
        )

        TimerScreen(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.CenterHorizontally))
    }


}

@Composable
@Preview
fun UserContent()
{
    Box(modifier = Modifier.background(Color.Gray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center){
        Image(
            painterResource(id = R.drawable.bronze),
            contentDescription = "青铜边框"
        )
    }
    Column (modifier = Modifier
        .fillMaxWidth()
        .height(600.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Image(painterResource(id = R.drawable.rank), contentDescription = "头像", modifier = Modifier
            .padding(top = 108.dp)
            .size(180.dp)
            .align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(108.dp))
        Text(text = "name", fontSize = 48.sp, modifier = Modifier
            .align(Alignment.CenterHorizontally))

    }
}

@Composable
fun RepeatingRippleButton(modifier: Modifier,matchViewModel: MatchViewModel = hiltViewModel()) {
    val interactionSource = remember { MutableInteractionSource() }
    var isRippleActive by remember { mutableStateOf(false) }
    val isRunning by matchViewModel.isRunning.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRippleActive) {
        if (!isRippleActive) {
            interactionSource.clearRipples()
        }
        while (isRippleActive) {
            interactionSource.emitPress()
            delay(500) // 控制波纹效果的频率
            interactionSource.emitRelease()
            delay(500)
        }
    }

    CompositionLocalProvider(LocalRippleTheme provides RippleButton.CustomRippleTheme) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(24.dp)) // 使用 dp 而不是 float
                .background(Color("#99EBFF".toColorInt()))
                .clickable {
                    isRippleActive = !isRippleActive
                    matchViewModel.matching()
                    if (isRunning) {
                        matchViewModel.resetTimer()
                    } else
                        matchViewModel.startTimer()
                }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = true)
                )
        ) {
            Text(
                text = "匹配",
                fontSize = 36.sp,
                modifier = Modifier.align(Alignment.Center),
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun TimerScreen(modifier: Modifier,matchViewModel: MatchViewModel = hiltViewModel()) {
    val time by matchViewModel.time.collectAsState()
    val isRunning by matchViewModel.isRunning.collectAsState()

    AnimatedVisibility(visible = isRunning) {
        Row (modifier){
            Box(modifier = Modifier.fillMaxSize()){
                Text(text = "正在匹配：$time",modifier=Modifier.align(Alignment.Center), fontSize = 24.sp)
            }
        }
    }

}



