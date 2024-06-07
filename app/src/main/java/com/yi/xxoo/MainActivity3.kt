package com.yi.xxoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.yi.xxoo.ui.theme.XxooTheme

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = mutableStateOf(List(999) { it })
        var lastDisposed by mutableIntStateOf(-1)
        setContent {
            Column {
                Text(text = "last disposed: $lastDisposed", fontSize = 40.sp)
                LazyColumn {
                    items(
                        list.value.size,
                        key = { list.value[it] },
                        contentType = { 0 }
                    ) {
                        Text(text = "hello ${list.value[it]}", fontSize = 20.sp)
                        DisposableEffect(true) {
                            onDispose {
                                lastDisposed = it
                            }
                        }
                    }
                }
            }
//            state.Content()
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XxooTheme {
        Greeting("Android")
    }
}