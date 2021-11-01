package com.github.whyrising.app.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.github.whyrising.recompose.regSub
import com.github.whyrising.recompose.regSubM
import com.github.whyrising.recompose.subscribe
import com.github.whyrising.y.collections.core.v
import java.util.*

fun capitalize(text: String) = text.replaceFirstChar {
    when {
        it.isLowerCase() -> it.titlecase(Locale.getDefault())
        else -> it.toString()
    }
}

fun regHomeSubs() {
    regSub<DbSchema, String>(
        queryId = ":screen-title",
    ) { db, _ ->
        db.screenTitle
    }

    regSub<String, String>(
        queryId = ":format-screen-title",
        signalsFn = {
            subscribe(v(":screen-title"))
        }
    ) { title, _ ->
        title.replaceFirstChar { it.uppercase() }
    }

    regSub<DbSchema, Boolean>(
        queryId = ":is-about-btn-enabled",
    ) { db, _ ->
        db.home.isAboutBtnEnabled
    }

    regSub<DbSchema, String>(
        queryId = ":greeting",
    ) { db, _ ->
        db.home.greeting
    }

    regSub<DbSchema, String>(
        queryId = ":android-api",
    ) { db, _ ->
        db.home.androidApi
    }

    regSubM<String, AnnotatedString>(
        queryId = ":android-greeting",
        signalsFn = {
            v(
                subscribe(v(":greeting")),
                subscribe(v(":android-api")),
            )
        },
    ) { (greeting, androidApi), (_, color) ->
        buildAnnotatedString {
            val style = SpanStyle(fontSize = 32.sp)
            val space = " "

            withStyle(style) {
                append(capitalize(greeting))
            }

            append(space)

            withStyle(
                style.copy(
                    color = color as Color,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(androidApi)
            }

            append(space)

            withStyle(style) {
                append("\uD83D\uDE00")
            }
        }
    }
}
