package com.why.template.compose.recompose.registrar

import com.why.template.compose.recompose.registrar.Kinds.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs

class RegistrarTest : FreeSpec({
    "registerHandler()/getHandler(kind)" - {
        "Fx kind" {
            val id = ":fx"
            val handlerFn = { _: Any -> }
            registerHandler(id, Fx, handlerFn)

            val handler = getHandler(Fx, id)

            handler shouldBeSameInstanceAs handlerFn
        }

        "Event kind" {
            val id = ":event"
            val interceptors = listOf<Any>()
            registerHandler(id, Event, interceptors)

            val handler = getHandler(Event, id)

            handler shouldBeSameInstanceAs interceptors
        }

        "Cofx kind" {
            val id = ":cofx"
            val handlerFn: (Any) -> Any = { _ -> }
            registerHandler(id, Cofx, handlerFn)

            val handler = getHandler(Cofx, id)

            handler shouldBeSameInstanceAs handlerFn
        }

        "Sub kind" {
            val id = ":sub"
            val handlerFn: (Any, ArrayList<Any>) -> Any = { _, _ -> }
            registerHandler(id, Sub, handlerFn)

            val handler = getHandler(Sub, id)

            handler shouldBeSameInstanceAs handlerFn
        }

        "two kinds of handlers with same id should register separately" {
            val id = ":id"
            val handlerFn1 = { _: Any -> }
            val handlerFn2 = { _: Any -> }
            val handlerFn3 = { _: Any -> }
            val handlerFn4 = { _: Any -> }
            registerHandler(id, Fx, handlerFn1)
            registerHandler(id, Cofx, handlerFn2)
            registerHandler(id, Event, handlerFn3)
            registerHandler(id, Sub, handlerFn4)

            val handler1 = getHandler(Fx, id)
            val handler2 = getHandler(Cofx, id)
            val handler3 = getHandler(Event, id)
            val handler4 = getHandler(Sub, id)

            handler1 shouldBeSameInstanceAs handlerFn1
            handler2 shouldBeSameInstanceAs handlerFn2
            handler3 shouldBeSameInstanceAs handlerFn3
            handler4 shouldBeSameInstanceAs handlerFn4

            handler1 shouldNotBeSameInstanceAs handler2
            handler2 shouldNotBeSameInstanceAs handler3
            handler3 shouldNotBeSameInstanceAs handler4
            handler4 shouldNotBeSameInstanceAs handler1
        }
    }
})
