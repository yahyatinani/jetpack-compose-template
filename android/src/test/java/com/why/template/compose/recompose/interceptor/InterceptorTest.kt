package com.why.template.compose.recompose.interceptor

import com.why.template.compose.recompose.Keys
import com.why.template.compose.recompose.Keys.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class InterceptorTest : FreeSpec({
    "context(event, interceptors) should return a fresh context" {
        val eventVec = arrayListOf<Any>(":id", 12)
        val interceptors: List<Map<Keys, Any>> = listOf()

        val context = context(eventVec, interceptors)

        context shouldBe mapOf(
            coeffects to mapOf(
                event to eventVec,
                originalEvent to eventVec
            ),
            queue to interceptors
        )
    }

    "changeDirection(context) should put the stack into a new the queue" {
        val context = mapOf<Keys, Any>(
            queue to listOf<Any>(),
            stack to listOf(1, 2, 3)
        )

        val c = changeDirection(context)

        c shouldBe mapOf<Keys, Any>(
            queue to listOf<Any>(1, 2, 3),
            stack to listOf(1, 2, 3)
        )
    }

    "invokeInterceptorFn() should call the interceptor fun based direction" - {
        val context0 = mapOf<Keys, Any>(
            queue to listOf<Any>(),
            stack to listOf(1, 2, 3)
        )

        val f: (context: Map<Keys, Any>) -> Map<Keys, Any> = { context ->
            val q = (context[queue] as List<Any>).plus(1)
            context.plus(queue to q)
        }

        val addToQAfter = toInterceptor(
            id = ":add-to-queue",
            after = f
        )

        "should call :before and add to the context" {
            val addToQ = toInterceptor(id = ":add-to-queue", before = f)

            val context = invokeInterceptorFn(context0, addToQ, before)

            context shouldBe mapOf<Keys, Any>(
                queue to listOf(1),
                stack to listOf(1, 2, 3)
            )
        }

        "should call :after and add to the context" {
            val context = invokeInterceptorFn(context0, addToQAfter, after)

            context shouldBe mapOf<Keys, Any>(
                queue to listOf(1),
                stack to listOf(1, 2, 3)
            )
        }

        "when some direction set to default, should return the same context" {
            val context = invokeInterceptorFn(context0, addToQAfter, before)

            context shouldBeSameInstanceAs context0
        }
    }
})
