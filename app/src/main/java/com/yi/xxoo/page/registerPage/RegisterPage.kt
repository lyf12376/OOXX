package com.yi.xxoo.page.registerPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yi.xxoo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.security.SecureRandom

@Composable
fun RegisterPage(navController: NavController, registerViewModel: RegisterViewModel = hiltViewModel()) {
    val scope1 = CoroutineScope(Dispatchers.IO)
    var success = false
    val sendMail = CoroutineScope(Dispatchers.IO)
    var showDialog by remember {
        mutableStateOf(false)
    }
    var inputCaptcha by remember {
        mutableStateOf("")
    }
    var account by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isAccountFocused by remember { mutableStateOf(false) }
    var password by remember {
        mutableStateOf("")
    }
    var isSending by remember {
        mutableStateOf(false)
    }
    var remainingSeconds by remember {
        mutableIntStateOf(30)
    }
    val textWidth = 300.dp
    val textHeight = 54.dp

    Box(
        Modifier
            .fillMaxSize()
            .background(Color("#343447".toColorInt()))
    ) {}

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Image(
                painterResource(id = R.drawable.back1),
                contentDescription = "返回",
                Modifier
                    .padding(start = 16.dp)
                    .clickable { navController.navigate("start") }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "注册",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.cute)),
                    color = Color("#b9b9b9".toColorInt())
                )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent)
        ) {

            BasicTextField(
                value = account,
                onValueChange = { account = it },
                modifier = Modifier
                    .width(textWidth)
                    .height(textHeight)
                    .background(Color.Transparent)
                    .focusTarget() // 监听焦点状态
                    .onFocusChanged { isAccountFocused = it.isFocused }
                    .drawWithContent { // 自定义绘制
                        drawContent() // 绘制原有的内容
                        drawLine(
                            color = Color.White, // 线的颜色
                            start = Offset(0f, size.height), // 线的起点，位于左下角
                            end = Offset(size.width, size.height) // 线的终点，位于右下角
                        )
                    },
                textStyle = TextStyle(Color.White, fontSize = 18.sp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row {
                        Icon(
                            painterResource(id = R.drawable.email),
                            contentDescription = "邮箱",
                            tint = Color.Unspecified,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(), // 填充所有可用空间
                            contentAlignment = Alignment.CenterStart // 水平和竖直居中
                        ) {
                            innerTextField() // 确保innerTextField在布局中占据了足够的空间
                        }
                        if (account != "") {
                            IconButton(onClick = { account = "" }) {
                                Icon(
                                    painterResource(id = R.drawable.clear),
                                    tint = Color.Unspecified,
                                    contentDescription = "清空"
                                )
                            }
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent)
        ) {
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .width(textWidth)
                    .height(textHeight)
                    .background(Color.Transparent)
                    .drawWithContent { // 自定义绘制
                        drawContent() // 绘制原有的内容
                        drawLine(
                            color = Color.White, // 线的颜色
                            start = Offset(0f, size.height), // 线的起点，位于左下角
                            end = Offset(size.width, size.height) // 线的终点，位于右下角
                        )
                    },
                textStyle = TextStyle(Color.White, fontSize = 18.sp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row {
                        Icon(
                            painterResource(id = R.drawable.password),
                            contentDescription = "密码",
                            tint = Color.Unspecified,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(), // 填充所有可用空间
                            contentAlignment = Alignment.CenterStart // 水平和竖直居中
                        ) {
                            innerTextField() // 确保innerTextField在布局中占据了足够的空间
                        }
                        if (password != "") {
                            IconButton(onClick = { password = "" }) {
                                Icon(
                                    painterResource(id = R.drawable.clear),
                                    tint = Color.Unspecified,
                                    contentDescription = "清空"
                                )
                            }
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent)
        ) {
            BasicTextField(
                value = inputCaptcha,
                onValueChange = { inputCaptcha = it },
                modifier = Modifier
                    .width(textWidth)
                    .height(textHeight)
                    .background(Color.Transparent)
                    .drawWithContent { // 自定义绘制
                        drawContent() // 绘制原有的内容
                        drawLine(
                            color = Color.White, // 线的颜色
                            start = Offset(0f, size.height), // 线的起点，位于左下角
                            end = Offset(size.width, size.height) // 线的终点，位于右下角
                        )
                    },
                textStyle = TextStyle(Color.White, fontSize = 18.sp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row {
                        Button(
                            onClick = {
                                sendMail.launch {
                                    if (!isValidEmail(account)) {
                                        showDialog = true
                                    } else {
                                        isSending = true
//                                        registerViewModel.sendMail(Captcha(toUserMail = account, where = "regist"))
                                    }
                                }
                            }, shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSending) Color(
                                    "#22222f".toColorInt()
                                ) else Color("#465ACA".toColorInt())
                            ),
                            modifier = Modifier.width(108.dp),
                            enabled = !isSending
                        ) {
                            if (isSending) {
                                Text(
                                    text = "${remainingSeconds}s", modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.cute)),
                                    color = Color("#b9b9b9".toColorInt())
                                )
                                LaunchedEffect(key1 = isSending) {
                                    while (remainingSeconds > 0) {
                                        delay(1000L)
                                        remainingSeconds--
                                    }
                                    isSending = false
                                }
                            } else {
                                remainingSeconds = 30
                                Text(
                                    text = "验证码",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.cute)),
                                    color = Color("#b9b9b9".toColorInt())
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(), // 填充所有可用空间
                            contentAlignment = Alignment.CenterStart // 水平和竖直居中
                        ) {
                            innerTextField() // 确保innerTextField在布局中占据了足够的空间
                        }
                        if (inputCaptcha != "") {
                            IconButton(onClick = { inputCaptcha = "" }) {
                                Icon(
                                    painterResource(id = R.drawable.clear),
                                    tint = Color.Unspecified,
                                    contentDescription = "清空"
                                )
                            }
                        }
                    }
                }
            )
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("错误") },
                text = { Text("邮箱地址无效或已经被注册，请输入正确的邮箱地址。") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("确定")
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                runBlocking {
                    scope1.launch {
//                        registerViewModel.register(User(nickName = "", phoneNumber = "", email = account, password = password, code = inputCaptcha))
                    }.join() // 等待协程结束
                    // 协程结束后进行判断
                }
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .width(textWidth)
                .height(textHeight)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color("#22222f".toColorInt()))
        ) {
            Text(
                text = "立即注册",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                color = Color("#b9b9b9".toColorInt())
            )
        }
    }
}


fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+\$")
    return email.matches(emailRegex)
}

fun generateFourDigitRandomNumber(): Int {
    val random = SecureRandom()
    Log.d("TAG", "generateFourDigitRandomNumber: ")
    return random.nextInt(9000) + 1000
}