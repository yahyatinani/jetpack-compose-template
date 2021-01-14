package com.why.template.compose.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Greeting(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(Modifier.align(Alignment.Center)) {
            Text(text = "Hello")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = "!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() = Greeting(name = "Jetpack Compose")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Greeting("Android 11")
        }
    }
}
