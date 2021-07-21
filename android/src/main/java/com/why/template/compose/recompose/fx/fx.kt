package com.why.template.compose.recompose.fx

import android.util.Log
import com.why.template.compose.recompose.Context
import com.why.template.compose.recompose.db.appDb
import com.why.template.compose.recompose.db.reset
import com.why.template.compose.recompose.interceptor.Interceptor
import com.why.template.compose.recompose.interceptor.toInterceptor
import com.why.template.compose.recompose.registrar.Kinds
import com.why.template.compose.recompose.registrar.fxHandlers
import com.why.template.compose.recompose.registrar.registerHandler

/*
-- Registration ----------------------------------------------------------------
 */
val kind: Kinds = Kinds.Fx

fun regFx(id: Any, handler: (value: Any) -> Unit) {
    registerHandler(id, kind, handler)
}

/*
-- Interceptor -----------------------------------------------------------------
 */

val doFx: Map<Interceptor, Any> = toInterceptor(
    id = ":do-fx",
    after = { context: Map<Context, Any> ->
        val effects = context[Context.Effects] as Map<*, *>
        val effectsWithoutDb = effects.minus(Context.Db)

        val newDb = effects[Context.Db]
        if (newDb != null) {
            val fxFn = fxHandlers[Context.Db] as (value: Any) -> Unit
            Log.i(
                "doFx",
                "$newDb"
            )
            fxFn(newDb)
        }

        for ((effectKey, effectValue) in effectsWithoutDb) {
            val fxFn = fxHandlers[effectKey] as ((value: Any) -> Unit)?

            if (fxFn != null && effectValue != null)
                fxFn(effectValue)
            else
                Log.i(
                    "re-compose",
                    "no handler registered for effect: $effectKey. Ignoring."
                )
        }
    }
)

/*
-- Builtin Effect Handlers ----------------------------------------------------
 */
val fx1: Unit = regFx(id = Context.Db) { value ->
    when {
        appDb != value -> reset(value)
        else -> Log.i("regFx", "Same appDb value")
    }
}
