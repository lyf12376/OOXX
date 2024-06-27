package com.yi.xxoo.page.loginPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yi.xxoo.R
import com.yi.xxoo.Room.savedUser.SavedUser
import com.yi.xxoo.navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var rememberPassword by remember {
        mutableStateOf(false)
    }
    var account by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var password by remember {
        mutableStateOf("")
    }
    val textWidth = 320.dp
    val textHeight = 50.dp
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val loginSuccess = loginViewModel.loginSuccess.collectAsState()
    val loginFailed = loginViewModel.loginFailed.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val savedUserList = loginViewModel.savedUserList.collectAsState(initial = emptyList())
    val network = loginViewModel.network.collectAsState()
    val startOfflineGame = loginViewModel.startOfflineGame.collectAsState()
    val registerOfflineAccount = loginViewModel.registerOfflineAccount.collectAsState()
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    if (!network.value){
        AlertDialog(
            onDismissRequest = { loginViewModel.setLoginFailed() },
            title = { Text("网络故障") },
            text = { Text("请检查网络或者开始离线游戏") },
            confirmButton = {
                Button(onClick = { loginViewModel.offlineMode() }) {
                    Text("离线模式")
                }
                Button(onClick = { loginViewModel.reConnect(context) }) {
                    Text(text = "重新连接")
                }
            }
        )
    }
    if (loginFailed.value) {
        AlertDialog(
            onDismissRequest = { loginViewModel.setLoginFailed() },
            title = { Text("错误") },
            text = { Text("账号或密码错误，或者网络请求出错，请重试。") },
            confirmButton = {
                Button(onClick = { loginViewModel.setLoginFailed() }) {
                    Text("确定")
                }
            }
        )
    }
    LaunchedEffect (true){
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }
    LaunchedEffect (startOfflineGame.value){
        if (startOfflineGame.value){
            navController.navigate(Screen.MinePage.route){
                popUpTo(Screen.LoginPage.route){
                    inclusive = true
                }
            }
        }
    }
    LaunchedEffect (registerOfflineAccount.value){
        if (registerOfflineAccount.value){
            navController.navigate(Screen.DocumentPage.route){
                popUpTo(Screen.LoginPage.route){
                    inclusive = true
                }
            }
        }
    }
    LaunchedEffect(loginSuccess.value) {
        if (loginSuccess.value) {
            navController.navigate("MinePage") {
                popUpTo("LoginPage"){
                    inclusive = true
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .noRippleClickable { focusManager.clearFocus() }
            .background(Color("#343447".toColorInt()))) {}
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        Image(
            painterResource(id = R.drawable.login_icon),
            contentDescription = "登陆图标",
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "OOXX——Game", modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.cute)),
            color = Color("#b9b9b9".toColorInt())
        )
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent)
        ) {
            BasicTextField(
                value = account,
                onValueChange = { account = it },
                modifier = androidx.compose.ui.Modifier
                    .width(textWidth)
                    .height(textHeight)
                    .background(Color.Transparent)
                    .focusRequester(focusRequester)
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

                        var isExpanded by remember {
                            mutableStateOf(false)
                        }
                        IconButton(
                            onClick = {
                                isExpanded = !isExpanded
                                if (savedUserList.value.isNotEmpty())
                                    expanded = !expanded
                            }
                        ) {
                            Icon(
                                painterResource(id = if (!isExpanded) R.drawable.pull_down else R.drawable.pull_up),
                                tint = Color.Unspecified,
                                contentDescription = "上/下拉"
                            )
                        }
                    }
                },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(textWidth) // 这里的宽度应该和你的TextField的宽度一样
            ) {
                savedUserList.value.forEach { savedUser ->
                    DropdownMenuItem(text = { Text(text = savedUser.account) },
                        onClick = {
                            account = savedUser.account
                            password = savedUser.password
                            expanded = false
                        },
                        trailingIcon = {
                            Icon(
                                painterResource(id = R.drawable.clear),
                                contentDescription = "取消保存",
                                modifier = Modifier.clickable {
                                    loginViewModel.deleteUser(savedUser.account)
                                }
                            )
                        }
                    )
                }
            }
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
                modifier = androidx.compose.ui.Modifier
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
        Button(
            onClick = {
                if (rememberPassword) {
                    loginViewModel.saveUser(SavedUser(account = account, password = password))
                }
                loginViewModel.isLogin(account, password)
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .width(textWidth)
                .height(textHeight)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color("#22222f".toColorInt()))
        ) {
            Text(
                text = "Log In",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                color = Color("#b9b9b9".toColorInt())
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        Row {
            Checkbox(
                checked = rememberPassword,
                onCheckedChange = { rememberPassword = it },
                modifier = Modifier
                    .padding(start = 32.dp),
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color("#b9b9b9".toColorInt()), // 设置复选框选中时的颜色
                    uncheckedColor = Color.Gray, // 设置复选框未选中时的颜色
                )
            )
            Text(
                text = "Remember Password",
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                color = Color("#b9b9b9".toColorInt())
            )
        }

        Text(
            text = "Forget Password",
            modifier = Modifier
                .align(Alignment.End)
                .clickable { }
                .padding(end = 54.dp),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.cute)),
            color = Color("#b9b9b9".toColorInt())
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Text(
                text = "Don't have a account?Create One!",
                modifier = Modifier.clickable {
                    navController.navigate(Screen.RegisterPage.route)
                },
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                color = Color("#b9b9b9".toColorInt())
            )
        }
    }
}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = this.then(
    Modifier.composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }
)