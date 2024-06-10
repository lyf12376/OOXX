package com.yi.xxoo.page.documentPage

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mr0xf00.easycrop.rememberImageCropper
import com.yi.xxoo.R
import com.yi.xxoo.page.registerPage.RegisterViewModel
import com.yi.xxoo.utils.pictrueUtils.PhotoComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun BasicMessage(navController: NavController, account : String,viewModel: RegisterViewModel = hiltViewModel()) {
    val textWidth = 300.dp
    val textHeight = 50.dp
    val scope1 = CoroutineScope(Dispatchers.IO)
    val list = listOf("女", "男", "未知")
    var navigae = false
    var showDialog1 by remember {
        mutableStateOf(false)
    }

    var showDialog2 by remember {
        mutableStateOf(false)
    }

    val mediaAction by lazy { PhotoComponent.instance }
    var localImgPath by remember{
        mutableStateOf(Uri.EMPTY)
    }

    val imageCropper = rememberImageCropper()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var name by remember {
        mutableStateOf("")
    }

    var gender by remember {
        mutableStateOf(0)
    }

    val context = LocalContext.current

    var profilePhotoChanged by remember {
        mutableStateOf(false)
    }

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
                    .align(Alignment.CenterVertically)
                    .clickable { navController.navigate("main") }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "完善资料",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.cute)),
                    color = Color("#b9b9b9".toColorInt())
                )
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
        Box(modifier = Modifier
            .size(120.dp)
            .align(Alignment.CenterHorizontally)) {
            if (localImgPath == Uri.EMPTY) {
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            AsyncImage(
                model = localImgPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(0.dp))
                    .clickable {
                        showDialog2 = true
                    },
                contentScale = ContentScale.FillBounds,
            )
        }


        mediaAction.SelectImage(
            galleryCallback = {
                "相册内容${it}"
                if (it.isSuccess) {
                    localImgPath = it.uri
                }
            },
            graphCallback = {
                "拍照内容${it.uri}"
                if (it.isSuccess) {
                    localImgPath = it.uri
                }
            },
            permissionRationale = {
                //权限拒绝的处理
            }
        )


        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color.Transparent)
        ) {
            BasicTextField(
                value = name,
                onValueChange = {
                    if (it.length <= 10) {
                        name = it
                    }
                },
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
                    Row() {
                        Text(
                            text = "昵称",
                            modifier = Modifier.padding(15.dp),
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.cute)),
                            color = Color("#b9b9b9".toColorInt())
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(), // 填充所有可用空间
                            contentAlignment = Alignment.CenterStart // 水平和竖直居中
                        ) {
                            innerTextField() // 确保innerTextField在布局中占据了足够的空间
                        }
                        if (name != "") {
                            IconButton(onClick = { name = "" }) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "2~10个字符长度",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 64.dp),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.cute)),
            color = Color("#b9b9b9".toColorInt())
        )
        Spacer(modifier = Modifier.height(36.dp))
        if (showDialog1) {
            AlertDialog(
                onDismissRequest = { showDialog1 = false },
                title = { Text("错误") },
                text = { Text("请正确的填写个人信息。") },
                confirmButton = {
                    Button(onClick = { showDialog1 = false }) {
                        Text("确定")
                    }
                }
            )
        }
        if (showDialog2) {
            AlertDialog(
                onDismissRequest = { showDialog2 = false },
                title = { Text("头像") },
                text = { Text("请选择你的头像。") },
                confirmButton = {
                    Button(onClick = {
                        showDialog2 = false
                        mediaAction.takePhoto()
                    }) {
                        Text("拍照")
                    }
                    Button(onClick = {
                        showDialog2 = false
                        mediaAction.selectImage()
                    }) {
                        Text(text = "相册")
                    }
                }
            )
        }
        Button(
            onClick = {
                if (name.length < 2 || name.length >10 || localImgPath == null)
                {
                    showDialog1 = true
                }
                else {
                    runBlocking {
                        scope1.launch {
                            Log.d("TAG", "BasicMessage: ${getRealFilePath(context, localImgPath)}")
                            getRealFilePath(context, localImgPath)?.let {
                                viewModel.updatePersonalInformation(account, name, it)
                            }
                        }.join()
                        if (navigae)
                            navController.navigate("LevelPage")
                    }
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
                text = "Go！",
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.cute)),
                color = Color("#b9b9b9".toColorInt())
            )
        }

    }

}

fun getPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val column_index: Int = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: 0
        cursor?.moveToFirst()
        return cursor?.getString(column_index)
    } finally {
        cursor?.close()
    }
}

fun readTextFromUri(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    var line: String? = reader.readLine()
    while (line != null) {
        stringBuilder.append(line)
        line = reader.readLine()
    }
    reader.close()
    inputStream?.close()
    return stringBuilder.toString()
}


/**
 * @param context
 * @param uri
 * @return 文件绝对路径或者null
 */
private fun getRealFilePath(context: Context, uri: Uri?): String? {
    if (null == uri) return null
    val scheme = uri.scheme
    var data: String? = null
    if (scheme == null) data = uri.path else if (ContentResolver.SCHEME_FILE == scheme) {
        data = uri.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.ImageColumns.DATA),
            null,
            null,
            null
        )
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                }
            }
            cursor.close()
        }
    }
    return data
}