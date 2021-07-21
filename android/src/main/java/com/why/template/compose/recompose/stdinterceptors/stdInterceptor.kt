package com.why.template.compose.recompose.stdinterceptors

import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.*
import com.why.template.compose.recompose.interceptor.Interceptor
import com.why.template.compose.recompose.interceptor.toInterceptor

/*
-- Interceptor Factories -----------------------------------------------------

These 2 factories wrap the 2 kinds of event handlers.

 */

fun appDbNotInitialized(oldDb: Any?) = oldDb is Int

inline fun <reified T> dbHandlerToInterceptor(
    crossinline handlerFn: (db: T?, vec: ArrayList<Any>) -> T
): Map<Interceptor, Any> = toInterceptor(
    id = ":db-handler",
    before = { keys: Map<Keys, Any> ->
        val cofx = keys[coeffects] as Map<*, *>
        val oldDb = cofx[db]
        val event = cofx[event] as ArrayList<Any>

        val newDb: T = when {
            appDbNotInitialized(oldDb) -> handlerFn(null, event)
            else -> handlerFn(oldDb as T, event)
        }

        val fx = keys[effects] as Map<*, *>

        val newContext = fx.plus(db to newDb).let { newFx ->
            keys.plus(effects to newFx)
        }

        newContext
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
