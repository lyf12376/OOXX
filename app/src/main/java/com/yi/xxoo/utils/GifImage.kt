package com.yi.xxoo.utils

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.yi.xxoo.R

@Composable
fun GifImage(modifier: Modifier, imageId : Int) {
    //自己构建图片加载器
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()
    Image(
        modifier = modifier,
        painter = //淡出效果
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageId)
                .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
                    crossfade(true)//淡出效果
                }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
    )
}