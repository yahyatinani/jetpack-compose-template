package com.why.template.compose.view.samples.customlayout.constraintlayout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.why.template.compose.view.common.MyApp

@Composable
fun ConstraintLayoutContent1() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        Text(
            text = "Text",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}

@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.3f)
        Text(
            text = "This is a very very very very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
    BoxWithConstraints {

    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet = ConstraintSet {
    val button = createRefFor("button")
    val text = createRefFor("text")

    constrain(button) {
        top.linkTo(parent.top, margin = margin)
    }
    constrain(text) {
        top.linkTo(button.bottom, margin = margin)
    }
}

@Composable
private fun BoxWithConstraintsScope.isPortrait() = maxWidth < maxHeight

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = when {
            isPortrait() -> decoupledConstraints(margin = 16.dp)
            else -> decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = {},
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ConstraintLayoutContent1Preview() {
    ConstraintLayoutContent1()
}

@Composable
@Preview(showBackground = true)
fun ConstraintLayoutContent2Preview() {
    ConstraintLayoutContent2()
}

@Composable
@Preview(showBackground = true)
fun LargeConstraintLayoutPreview() {
    MyApp {
        LargeConstraintLayout()
    }
}

@Composable
@Preview(showBackground = true)
fun DecoupledConstraintLayoutPreview() {
    MyApp {
        DecoupledConstraintLayout()
    }
}
