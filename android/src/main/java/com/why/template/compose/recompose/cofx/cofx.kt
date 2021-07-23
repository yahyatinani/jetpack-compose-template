package com.why.template.compose.recompose.cofx

import android.util.Log
import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.coeffects
import com.why.template.compose.recompose.Keys.db
import com.why.template.compose.recompose.db.appDb
import com.why.template.compose.recompose.interceptor.toInterceptor
import com.why.template.compose.recompose.registrar.Kinds
import com.why.template.compose.recompose.registrar.Kinds.Cofx
import com.why.template.compose.recompose.registrar.getHandler
import com.why.template.compose.recompose.registrar.registerHandler

/*
---------- Registration ----------------
 */
val kind: Kinds = Cofx

fun regCofx(id: Any, handler: (coeffects: Map<Any, Any>) -> Any) {
    registerHandler(id, kind, handler)
}

/*
------------- Interceptor ---------------
 */
fun injectCofx(id: Any): Map<Keys, Any> = toInterceptor(
    id = coeffects,
    before = { context ->
        val handler = getHandler(kind, id) as ((Any) -> Any)?
        if (handler != null) {
            val cofx = context[coeffects] ?: mapOf<Any, Any>()
            val newCofx = handler(cofx)
            val newContext = context.plus(coeffects to newCofx)

            newContext
        } else {
            Log.e("injectCofx", "No cofx handler registered for $id")
            context
        }
    }
)

/*
------------ Builtin CoEffects Handlers --------------
 */

// Because this interceptor is used so much, we reify it
val injectDb = injectCofx(id = db)

/*
 Adds to coeffects the value in `appDdb`, under the key `Db`
 */
val cofx1 = regCofx(id = db) { coeffects ->
    Log.e("regCofx", "Db")
    coeffects.plus(db to appDb)
}
