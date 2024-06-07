package com.yi.xxoo.page.gamePage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun GamePage() {

}

@Composable
@Preview
fun SixBySixGrid() {
    val gridSize = 6
    val gridState = remember { mutableStateListOf(*Array(gridSize * gridSize) { "" }) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(36) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .size(50.dp)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .clickable {
                        // 当单元格被点击时，切换状态
                        gridState[index] = if (gridState[index] == "x") "o" else "x"
                    }
            ) {
                Text(
                    text = gridState[index],
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
    }
}