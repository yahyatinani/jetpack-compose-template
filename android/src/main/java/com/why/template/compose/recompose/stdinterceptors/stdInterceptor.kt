package com.why.template.compose.recompose.stdinterceptors

import com.why.template.compose.recompose.Context
import com.why.template.compose.recompose.Context.*
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
    before = { context: Map<Context, Any> ->
        val cofx = context[Coeffects] as Map<*, *>
        val oldDb = cofx[Db]
        val event = cofx[Event] as ArrayList<Any>

        val newDb: T = when {
            appDbNotInitialized(oldDb) -> handlerFn(null, event)
            else -> handlerFn(oldDb as T, event)
        }

        val fx = context[Effects] as Map<*, *>

        val newContext = fx.plus(Db to newDb).let { newFx ->
            context.plus(Effects to newFx)
        }

        newContext
    }
)

fun fxHandlerToInterceptor(
    handlerFn: (cofx: Map<Any, Any>, event: ArrayList<Any>) -> Map<Any, Any>
): Any = toInterceptor(
    id = ":fx-handler",
    before = { context ->
        val cofx = context[Coeffects] as Map<Any, Any>
        val event = cofx[Event] as ArrayList<Any>

        val fxData = handlerFn(cofx, event)

        val newContext = context.plus(Effects to fxData)

        newContext
    }
)
