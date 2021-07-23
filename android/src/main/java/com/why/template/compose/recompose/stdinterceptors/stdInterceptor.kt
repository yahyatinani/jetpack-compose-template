package com.why.template.compose.recompose.stdinterceptors

import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.*
import com.why.template.compose.recompose.interceptor.toInterceptor

/*
-- Interceptor Factories -----------------------------------------------------

These 2 factories wrap the 2 kinds of event handlers.

 */

fun dbHandlerToInterceptor(
    handlerFn: (db: Any, vec: ArrayList<Any>) -> Any
): Map<Keys, Any> = toInterceptor(
    id = ":db-handler",
    before = { context: Map<Keys, Any> ->
        val cofx = context[coeffects] as Map<*, *>
        val oldDb = cofx[db]
        val event = cofx[event] as ArrayList<Any>

        val newDb = handlerFn(oldDb!!, event)

        val fx = (context[effects] ?: mapOf<Any, Any>()) as Map<Keys, Any>

        val newFx = fx.plus(db to newDb)

        context.plus(effects to newFx)
    }
)

fun fxHandlerToInterceptor(
    handlerFn: (cofx: Map<Any, Any>, event: ArrayList<Any>) -> Map<Any, Any>
): Any = toInterceptor(
    id = ":fx-handler",
    before = { context ->
        val cofx = context[coeffects] as Map<Any, Any>
        val event = cofx[event] as ArrayList<Any>

        val fxData = handlerFn(cofx, event)

        val newContext = context.plus(effects to fxData)

        newContext
    }
)
