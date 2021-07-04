package com.why.template.compose.view.samples.customlayout.constraintlayout

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutContent() {
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
@Preview(showBackground = true)
fun ConstraintLayoutContentPreview() {
    ConstraintLayoutContent()
}
