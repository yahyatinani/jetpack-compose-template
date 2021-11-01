package com.github.whyrising.app.home

import android.os.Build
import com.github.whyrising.recompose.cofx.regCofx

data class DbHomeSchema(
    val isAboutBtnEnabled: Boolean,
    val greeting: String,
    val androidApi: String,
)

data class DbSchema(
    val screenTitle: String,
    val home: DbHomeSchema,
)

val defaultDb = DbSchema(
    screenTitle = "",
    home = DbHomeSchema(
        isAboutBtnEnabled = true,
        greeting = "hello",
        androidApi = ""
    ),
)

// -- cofx Registrations -------------------------------------------------------

fun regHomeCofx() {
    regCofx(id = ":android-api") { coeffects ->
        coeffects.assoc(":android-api", "Android ${Build.VERSION.SDK_INT}")
    }
}
