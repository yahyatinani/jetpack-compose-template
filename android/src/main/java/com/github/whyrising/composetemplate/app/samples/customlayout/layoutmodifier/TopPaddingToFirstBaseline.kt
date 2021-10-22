package com.github.whyrising.composetemplate.app.samples.customlayout.layoutmodifier

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  Layout modifier is useful to control how a single composable gets measured
 *  and laid out on the screen.
 * */
fun Modifier.paddingFromBaseline(top: Dp) = this.then(
    layout { measurable: Measurable, constraints: Constraints ->
        val composable = measurable.measure(constraints)

        val firstBaseline = composable[FirstBaseline]
        check(firstBaseline != AlignmentLine.Unspecified)

        val composableY = top.roundToPx() - firstBaseline
        val height = composableY + composable.height

        layout(composable.width, height) {
            composable.placeRelative(0, composableY)
        }
    }
)

@Composable
@Preview(showBackground = true)
fun TextWithPaddingToBaselinePreview() {
    Text("Hi there!", Modifier.paddingFromBaseline(32.dp))
}

@Composable
@Preview(showBackground = true)
fun TextWithNormalPaddingPreview() {
    Text("Hi there!", Modifier.padding(top = 32.dp))
}
