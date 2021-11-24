package com.github.jahongk.week2_1

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.*

// How to create a modifier
@Stable
fun Modifier.padding(all: Dp) = this.then(
    PaddingModifier(start = all, top = all, end = all, bottom = all, rtlAware = true)
)


// PaddingModifier is a regular class
// that implements equals()
// so the modifier can be compared across recompositions.
private class PaddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean,
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        // 상하좌우로 들어온 패딩값을 더해서 전체 레이아웃의 width, height를 잡고, 새로운 사이즈의 레이아웃으로 반환
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)

        return layout(width, height) {
            if (rtlAware) {
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }
}

@Composable
fun BodyContent4(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            // modifier 순서 이슈.
            .size(200.dp) //  StaggeredGrid will be exactly 168x168 dp.
            .padding(16.dp)
            //.size(200.dp) // The StaggeredGrid size is 200x200 dp
            .horizontalScroll(rememberScrollState())
    ) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}