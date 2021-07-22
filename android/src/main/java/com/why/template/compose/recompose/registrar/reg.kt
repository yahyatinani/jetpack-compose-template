package com.why.template.compose.recompose.registrar

import android.util.Log
import com.why.template.compose.recompose.registrar.Kinds.*
import java.util.concurrent.ConcurrentHashMap

val eventHandlers = ConcurrentHashMap<Any, Any>()
val fxHandlers = ConcurrentHashMap<Any, Any>()
val cofxHandlers = ConcurrentHashMap<Any, Any>()
val subHandlers = ConcurrentHashMap<Any, Any>()

enum class Kinds { Event, Fx, Cofx, Sub }

fun getHandler(kind: Kinds, id: Any): Any? = when (kind) {
    Event -> eventHandlers[id]
    Cofx -> cofxHandlers[id]
    Sub -> subHandlers[id]
    Fx -> fxHandlers[id]
}

internal fun registerHandler(
    id: Any,
    kind: Kinds,
    handlerFn: Any
) {
    when (kind) {
        Event -> {
            if (eventHandlers[id] != null)
                Log.w("regEventDb: ", "overwriting handler for: $id")

            eventHandlers[id] = handlerFn
        }
        Fx -> {
            if (fxHandlers[id] != null)
                Log.w("regFx: ", "overwriting handler for: $id")
            fxHandlers[id] = handlerFn
        }
        Cofx -> {
            if (cofxHandlers[id] != null)
                Log.w("regCofx: ", "overwriting handler for: $id")

            cofxHandlers[id] = handlerFn
        }
        Sub -> {
            if (subHandlers[id] != null)
                Log.w("regSub: ", "overwriting handler for: $id")

            subHandlers[id] = handlerFn
        }
    }
}
