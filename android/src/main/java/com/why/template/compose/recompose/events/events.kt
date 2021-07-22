package com.why.template.compose.recompose.events

import com.why.template.compose.recompose.interceptor.Interceptor
import com.why.template.compose.recompose.interceptor.execute
import com.why.template.compose.recompose.registrar.Kinds
import com.why.template.compose.recompose.registrar.Kinds.Event
import com.why.template.compose.recompose.registrar.getHandler
import com.why.template.compose.recompose.registrar.registerHandler

val kind: Kinds = Event

private fun flatten(interceptors: ArrayList<Any>) =
    interceptors.flatMap { element: Any ->
        when (element) {
            is ArrayList<*> -> element as ArrayList<Map<Interceptor, Any>>
            else -> arrayListOf(element as Map<Interceptor, Any>)
        }
    }

/***
 * Associate the given event `id` with the given collection of `interceptors`.
 */
fun register(id: Any, interceptors: ArrayList<Any>) {
    registerHandler(id, kind, flatten(interceptors))
}

/*
-------------  Handle event ----------------------
 */

fun handle(eventVec: ArrayList<Any>) {
    val eventId = eventVec[0]
    val handler: Any = getHandler(kind, eventId) ?: return

    val chainOfInterceptors = handler as List<Map<Interceptor, Any>>
    execute(eventVec, chainOfInterceptors)
}

fun event(id: Any, vararg args: Any) = arrayListOf(
    id,
    *args
)
