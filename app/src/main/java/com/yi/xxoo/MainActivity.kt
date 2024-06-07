package com.yi.xxoo

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val d = windowManager.defaultDisplay
        val realDisplayMetrics = DisplayMetrics()
        d.getRealMetrics(realDisplayMetrics)
        val displayMetrics = DisplayMetrics()
        d.getMetrics(displayMetrics)
        val displayHeight = px2dip(this, displayMetrics.heightPixels)
        val displayWidth = px2dip(this, displayMetrics.widthPixels)
        ScreenData.screenWidthDp = displayWidth.dp
//        ScreenData.screenHeightDp = displayHeight.dp
        val padding = 20.dp
        val k = ScreenData.screenWidthDp - padding * 2
        val line = 5
        setContent {
            Column (modifier = Modifier.padding(padding)){
                for (i in 0 until line * 2) {
                    Row {
                        for (j in 0 until line) {
                            rec(k / 5, k / 5)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Composable
    fun rec(height: Dp, width: Dp) {
//    Log.d("TAG", "rec: $height, $width")
        val symbol = remember { mutableStateOf("") }
        Box(modifier = Modifier) {
            //给四周加上黑色直线
            Canvas(
                modifier = Modifier
                    .height(height)
                    .width(width)
                    .align(Alignment.Center)
            ) {
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 16f,
                    color = Color.Black
                )
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 16f,
                    color = Color.Black
                )
                drawLine(
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = 16f,
                    color = Color.Black
                )
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 16f,
                    color = Color.Black
                )
            }

            TransparentTextField(
                modifier = Modifier.align(Alignment.Center),
                height = height / 2,
                width = width / 2
            )

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TransparentTextField(modifier: Modifier, height: Dp, width: Dp) {
        var text by remember { mutableStateOf("") }

        Text(
            text = text, modifier = modifier.padding(2.dp)
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier
                .height(height)
                .width(width)
                .alpha(0f),
        )
    }

    private fun px2dip(context: Context, pxValue: Int): Int {
        // 获取当前手机的像素密度（1个dp对应几个px）
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt() // 四舍五入取整
    }
}