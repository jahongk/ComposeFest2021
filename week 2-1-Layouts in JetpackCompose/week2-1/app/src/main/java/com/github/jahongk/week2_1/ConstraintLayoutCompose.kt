package com.github.jahongk.week2_1

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.github.jahongk.week2_1.ui.theme.Week21Theme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {
                /* Do something */
            },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button 1")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            // Centers Text horizontally in the ConstraintLayout
            // centerHorizontallyTo(parent)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    Week21Theme {
        ConstraintLayoutContent()
    }
}


@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    Week21Theme {
        LargeConstraintLayout()
    }
}

@Composable
fun DecoupledConstaraintLayout() {
    BoxWithConstraints {
        val constraint = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp)// Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp)// Landscape constraints
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}