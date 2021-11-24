package com.github.jahongk.week2_1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.jahongk.week2_1.ui.theme.Week21Theme

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measuralbes, constraints ->
        val rowWidth = IntArray(rows) { 0 }
        val rowHeight = IntArray(rows) { 0 }

        // child measure
        val placeables = measuralbes.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val row = index % rows
            // 해당 row에 추가되는 아이템들의 길이를 모두 더해서 전체길이 저장
            rowWidth[row] += placeable.width
            // 같은 줄에서 제일 높은 놈으로 결정
            rowHeight[row] = Math.max(rowHeight[row], placeable.height)

            placeable
        }

        // 전체그리드의 넓이 is the widest row
        val width = rowWidth.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // 전체 그리드의 높이 is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeight.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // 각 row의 Y좌표, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeight[i - 1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(rowX[row], rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))

            Text(text = text)
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    Week21Theme {
        Chip(text = "Hi there!")
    }
}


//Now, let's create a list of topics that we can show in our BodyContent and display them in the StaggeredGrid:
val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun BodyContent3(modifier: Modifier = Modifier) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid(modifier, rows = 5) {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}

@Preview
@Composable
fun ComplexCustomLayoutPreview() {
    Week21Theme {
        BodyContent3()
    }
}