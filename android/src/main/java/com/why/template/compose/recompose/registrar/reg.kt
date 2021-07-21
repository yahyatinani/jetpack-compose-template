package com.why.template.compose.recompose.registrar

import android.util.Log
import com.why.template.compose.recompose.registrar.Kinds.*
import java.util.concurrent.ConcurrentHashMap

val dbEvent = ConcurrentHashMap<Any, Any>()
val fxEvent = ConcurrentHashMap<Any, Any>()

val fxHandlers = ConcurrentHashMap<Any, Any>()
val cofxHandlers = ConcurrentHashMap<Any, Any>()
val queryFns = ConcurrentHashMap<Any, Any>()
val memSubComp = ConcurrentHashMap<Any, Any>()

enum class Kinds {
    Event,
    Fx,
    Cofx,
    Sub
}

internal fun registerHandler(
    id: Any,
    kind: Kinds,
    handlerFn: Any
) {
    when (kind) {
        Event -> {
            if (dbEvent[id] != null)
                Log.w("regEventDb: ", "overwriting handler for: $id")

            dbEvent[id] = handlerFn
        }
        Fx -> {
            Log.i("regFx", "$id")

            if (dbEvent[id] != null)
                Log.w("regFx: ", "overwriting handler for: $id")
            fxHandlers[id] = handlerFn
        }
        Cofx -> {
            Log.i("regCofx", "$id")

            if (cofxHandlers[id] != null)
                Log.w("regCofx: ", "overwriting handler for: $id")

            cofxHandlers[id] = handlerFn
        }
        Sub -> TODO()
    }
}
