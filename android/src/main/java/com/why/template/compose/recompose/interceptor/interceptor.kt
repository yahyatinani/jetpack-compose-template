package com.why.template.compose.recompose.interceptor

import android.util.Log
import com.github.whyrising.y.concretions.list.PersistentList
import com.github.whyrising.y.concretions.list.l
import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.*

fun toInterceptor(
    id: Any,
    before: (context: Map<Keys, Any>) -> Map<Keys, Any> = { it },
    after: (context: Map<Keys, Any>) -> Any = { it }
): Map<Keys, Any> = mapOf(
    Keys.id to id,
    Keys.before to before,
    Keys.after to after,
)

fun assocCofx(context: Map<Keys, Any>, key: Any, value: Any): Map<Keys, Any> {
    val cofx = context[coeffects] as Map<Keys, Any>?

    val newCofx: Map<Any, Any> = cofx?.plus(key to value) ?: mapOf(key to value)

    return context.plus(coeffects to newCofx)
}

private fun enqueue(
    context: Map<Keys, Any>,
    interceptors: Any
) = context.plus(queue to interceptors)

/**
 * Create a fresh context.
 */
internal fun context(
    eventVec: Any,
    interceptors: List<Map<Keys, Any>>
): Map<Keys, Any> {
    val context0 = mapOf<Keys, Any>()
    val context1 = assocCofx(context0, event, eventVec)
    val context2 = assocCofx(context1, originalEvent, eventVec)

    return enqueue(context2, interceptors)
}

// -- Execute Interceptor Chain  ----------------------------------------------

internal fun invokeInterceptorFn(
    context: Map<Keys, Any>,
    interceptor: Map<Keys, Any>,
    direction: Keys
): Map<Keys, Any> {
    Log.i("invokeInterceptorFn", direction.toString())
    val f = interceptor[direction] as (Map<Keys, Any>) -> Any
    val r = f(context)

    return if (r is Map<*, *>) (r as Map<Keys, Any>) else context
}

internal fun invokeInterceptors(
    context: Map<Keys, Any>,
    direction: Keys
): Map<Keys, Any> {
    tailrec fun invokeInterceptors(
        context: Map<Keys, Any>
    ): Map<Keys, Any> {
        // TODO: Make sure a PersistentList is passed by callers
        val q = context[queue] as List<Map<Keys, Any>>
        return if (q.isEmpty()) context
        else {
            val interceptor: Map<Keys, Any> = q.first()
            val stk = (context[stack] ?: l<Any>()) as PersistentList<Any>
            val c = context
                .plus(queue to q.drop(1))
                .plus(stack to stk.conj(interceptor))

            val newContext = invokeInterceptorFn(c, interceptor, direction)

            invokeInterceptors(newContext)
        }
    }

    return invokeInterceptors(context)
}

internal fun changeDirection(context: Map<Keys, Any>): Map<Keys, Any> =
    enqueue(context, context[stack]!!)

fun execute(
    eventVec: ArrayList<Any>,
    interceptors: List<Map<Keys, Any>>
) {
    val context0 = context(eventVec, interceptors)
    val context1 = invokeInterceptors(context0, before)
    val context2 = changeDirection(context1)
    val context3 = invokeInterceptors(context2, after)
}
