package com.github.jahongk.week2_1

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.jahongk.week2_1.ui.theme.Week21Theme

// Using the layout modifier
fun Modifier.firstBaselineToTop(firstBaseLineToTop: Dp) = this.then(
    layout { measurable, constraints ->
        val placeable: Placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        // 조건에 안맞으면 IllegalStateException
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY: Int = firstBaseLineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY

        layout(placeable.width, height) {
            // [placeRelative] automatically adjusts the position of the placeable
            // based on the current [layoutDirection].

            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    Week21Theme {
        Text(text = "Hi there!", modifier = Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    Week21Theme {
        Text(text = "Hi there!", modifier = Modifier.padding(top = 32.dp))
    }
}


// Implementing a basic Column
// Using the Layout composable
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        // measure and position children given constraints logic here

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // place children
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Preview
@Composable
fun BodyContentPreview(modifier: Modifier = Modifier) {
    Week21Theme {
        BodyContent2(modifier)
    }
}
@Composable
fun BodyContent2(modifier: Modifier = Modifier) {
    MyOwnColumn(modifier = modifier.padding(8.dp)) {
        Text(text = "동해물과")
        Text(text = "백두산이")
        Text(text = "마르고 닳도록")
        Text(text = "하느님이 보우하사 우리나라 만세")
    }
}
