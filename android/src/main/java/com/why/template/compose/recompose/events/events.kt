package com.why.template.compose.recompose.events

import com.why.template.compose.recompose.Keys.fx
import com.why.template.compose.recompose.interceptor.Interceptor
import com.why.template.compose.recompose.interceptor.execute
import com.why.template.compose.recompose.registrar.*

val kind: Kinds = Kinds.Event

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
    val eventHandler: Any? = dbEvent[eventId]

    if (eventHandler != null) {
        val interceptors = eventHandler as List<Map<Interceptor, Any>>
        execute(eventVec, interceptors)

        return
    }

    val eventFx: Any? = fxEvent[eventId]

    // TODO: Reimplement
    if (eventFx != null) {
        val function = eventFx
                as (Map<Any, Any>, ArrayList<Any>) -> Map<Any, Any>

        val fxMap = function(mapOf(), eventVec)
        val fxVec = fxMap[fx] as ArrayList<ArrayList<Any>>
        fxVec.forEach { effectVec ->
            val id = effectVec[0]
            val value = effectVec[1]
            val fx = fxHandlers[id] as (value: Any) -> Unit
            fx(value)
        }

        return
    } else throw IllegalArgumentException(
        "Event handler not found for id: $eventId"
    )
}

fun event(id: Any, vararg args: Any) = arrayListOf(
    id,
    *args
)
