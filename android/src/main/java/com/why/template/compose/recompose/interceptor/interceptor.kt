package com.why.template.compose.recompose.interceptor

import android.util.Log
import com.why.template.compose.recompose.Context

enum class Interceptor {
    Id,
    Before,
    After
}

fun toInterceptor(
    id: Any,
    before: (context: Map<Context, Any>) -> Any = { it },
    after: (context: Map<Context, Any>) -> Any = { it }
): Map<Interceptor, Any> = mapOf(
    Interceptor.Id to id,
    Interceptor.Before to before,
    Interceptor.After to after,
)

private fun freshContext(event: Any) = mapOf(
    Context.Coeffects to mapOf<Any, Any>(Context.Event to event),
    Context.Effects to mapOf()
)

fun execute(
    eventVec: ArrayList<Any>,
    interceptors: List<Map<Interceptor, Any>>
) {
    val context1 =
        interceptors.fold(freshContext(eventVec)) { context, interceptor ->
            Log.i("interceptors:Before", "from $context")
            val beforeFn = interceptor[Interceptor.Before] as (Map<Context, Any>) -> Any

            val newContext = beforeFn(context)

            newContext as Map<Context, Map<Any, Any>>
        }

    // TODO: Optimize this using stack and queue
    interceptors.foldRight(context1) { interceptor, context ->
        Log.i("interceptors:After", "from $context")
        val afterFn = interceptor[Interceptor.After]
                as (Map<Context, Any>) -> Any

        val newContext = afterFn(context)
        if (newContext is Map<*, *>)
            newContext as Map<Context, Map<Any, Any>>
        else context1
    }
}
