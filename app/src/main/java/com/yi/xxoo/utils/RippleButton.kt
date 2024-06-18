package com.yi.xxoo.utils

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

object RippleButton {
    suspend fun MutableInteractionSource.emitPress() {
        // 使用 Offset 来手动控制波纹从中心发散
        emit(PressInteraction.Press(Offset(100f, 100f))) // 这里的 Offset 应该是 Box 的中心
    }

    suspend fun MutableInteractionSource.emitRelease() {
        emit(PressInteraction.Release(PressInteraction.Press(Offset(100f, 100f))))
    }

    suspend fun MutableInteractionSource.clearRipples() {
        // 清除所有的波纹效果
        emit(PressInteraction.Cancel(PressInteraction.Press(Offset(100f, 100f))))
    }

    object CustomRippleTheme : RippleTheme {
        @Composable
        override fun defaultColor(): Color = Color.Black

        @Composable
        override fun rippleAlpha(): RippleAlpha {
            return RippleAlpha(
                pressedAlpha = 0.22f,
                focusedAlpha = 0.22f,
                draggedAlpha = 0.22f,
                hoveredAlpha = 0.22f
            )
        }

        @Composable
        fun rippleDuration(): Int {
            return 300 // 控制波纹效果的持续时间，间接控制波纹的速率
        }
    }
}