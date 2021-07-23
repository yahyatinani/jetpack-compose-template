package com.why.template.compose.recompose.fx

import android.util.Log
import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.*
import com.why.template.compose.recompose.db.appDb
import com.why.template.compose.recompose.db.reset
import com.why.template.compose.recompose.dispatch
import com.why.template.compose.recompose.interceptor.toInterceptor
import com.why.template.compose.recompose.registrar.Kinds
import com.why.template.compose.recompose.registrar.getHandler
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

val doFx: Map<Keys, Any> = toInterceptor(
    id = dofx,
    after = { keys: Map<Keys, Any> ->
        val effects = keys[effects] as Map<Any, Any>
        val effectsWithoutDb: Map<Any, Any> = effects.minus(db)

        val newDb = effects[db]
        if (newDb != null) {
            val fxFn = getHandler(kind, db) as (value: Any) -> Unit
            Log.i(
                "doFx",
                "$newDb"
            )
            fxFn(newDb)
        }

        for ((effectKey, effectValue) in effectsWithoutDb) {
            val fxFn = getHandler(kind, effectKey) as ((value: Any) -> Unit)?

            when {
                fxFn != null -> fxFn(effectValue)
                else -> Log.i(
                    "re-compose",
                    "no handler registered for effect: $effectKey. Ignoring."
                )
            }
        }
    }
)

/*
-- Builtin Effect Handlers ----------------------------------------------------
 */
val fx1: Unit = regFx(id = fx) { listOfEffects: Any ->
    if (listOfEffects !is List<*>) {
        Log.e(
            "regFx",
            "\":fx\" effect expects a list, but was given " +
                    "${listOfEffects::class.java}"
        )
    } else {
        val effects: List<List<Any>> = listOfEffects as List<List<Any>>

        effects.forEach { effect: List<Any> ->
            val (effectKey, effectValue) = effect

            if (effectKey == db)
                Log.w("regFx", "\":fx\" effect should not contain a :db effect")

            val fxFn = getHandler(kind, effectKey) as ((value: Any) -> Unit)?

            when {
                fxFn != null -> fxFn(effectValue)
                else -> Log.i(
                    "regFx",
                    "in :fx no handler registered for effect: $effectKey. Ignoring."
                )
            }
        }
    }
}

val fx2: Unit = regFx(id = db) { value ->
    when {
        appDb != value -> reset(value)
        else -> Log.i("regFx", "Same appDb value")
    }
}

val fx3: Unit = regFx(id = dispatch) { value ->
    when (value) {
        is ArrayList<*> -> dispatch(value)
        else -> Log.e(
            "regFx",
            "ignoring bad :dispatch value. Expected an array list, but got: " +
                    "$value"
        )
    }
}

val fx4: Unit = regFx(id = dispatchN) { value ->
    when (value) {
        is List<*> -> {
            value.forEach { vec: Any? ->
                val event = vec as ArrayList<Any>
                dispatch(event)
            }
        }
        else -> Log.e(
            "regFx",
            "ignoring bad :dispatchN value. Expected a list, but got: " +
                    "$value"
        )
    }
}
