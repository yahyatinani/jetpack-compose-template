package com.why.template.compose.view.samples.customlayout.layoutcomposable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val composables = measurables.map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            composables.fold(0) { y, composable ->
                composable.placeRelative(0, y)
                y + composable.height
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TextWithPaddingToBaselinePreview() {
    MyOwnColumn(modifier = Modifier.padding(8.dp)) {
        Text("Item1")
        Text("Item2")
        Text("Item3")
        Text("Item4")
    }
}