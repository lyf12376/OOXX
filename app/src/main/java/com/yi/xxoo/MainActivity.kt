package com.yi.xxoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.navigation.NavigationGraph
import com.yi.xxoo.navigation.Screen
import com.yi.xxoo.ui.theme.XxooTheme
import com.yi.xxoo.utils.InitialOfflineGame
import com.yi.xxoo.utils.NetWorkUtils
import com.yi.xxoo.utils.getScreenData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.light(Color.Transparent.toArgb(), Color.Transparent.toArgb()) // light causes internally enforce the navigation bar to be fully transparent
        )
        setContent {
            val isInitial = InitialOfflineGame.isRoomDatabaseExist(this)
            val context = LocalContext.current
            LaunchedEffect (true){
                if (isInitial.not()) InitialOfflineGame.initialOfflineGame(context = context)
            }
            val navController = rememberNavController()
            GameMode.isNetworkEnabled = NetWorkUtils.isNetworkAvailable(this)
            getScreenData()
            NavigationGraph(
                navHostController = navController,
                startDestination = Screen.LoginPage.route
            )
        }
    }

    private fun startRepeatingTask() {
        lifecycleScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(5000) // 延迟5秒
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
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XxooTheme {
        Greeting("Android")
    }
}