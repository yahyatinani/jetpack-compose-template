package com.why.template.compose.recompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.why.template.compose.recompose.Context.*
import com.why.template.compose.recompose.Interceptor.*
import com.why.template.compose.recompose.db.MainViewModel
import com.why.template.compose.recompose.db.appDb
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

val dbEvent = ConcurrentHashMap<Any, Any>()
val fxEvent = ConcurrentHashMap<Any, Any>()

val fxHandlers = ConcurrentHashMap<Any, Any>()
val cofxHandlers = ConcurrentHashMap<Any, Any>()
val queryFns = ConcurrentHashMap<Any, Any>()
val memSubComp = ConcurrentHashMap<Any, Any>()

enum class Interceptor {
    Id,
    Before,
    After
}

enum class Context {
    Effects,
    Coeffects,
    Db,
    Event
}

fun regEventFx(
    id: Any,
    handler: (cofx: Map<Any, Any>, vec: ArrayList<Any>) -> Map<Any, Any>
) {
    Log.i("regEventFx", "$id")
    if (fxEvent[id] != null)
        Log.w("regEventFx: ", "overwriting handler for: $id")

    fxEvent[id] = handler

}

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
    val flatInterceptors = flatten(interceptors)

    if (dbEvent[id] != null)
        Log.w("regEventDb: ", "overwriting handler for: $id")

    dbEvent[id] = flatInterceptors
}

fun toInterceptor(
    id: Any,
    before: (context: Map<Context, Any>) -> Any = { it },
    after: (context: Map<Context, Any>) -> Any = { it }
): Map<Interceptor, Any> = mapOf(
    Id to id,
    Before to before,
    After to after,
)

/**
 * Context:
 *
 * {:coeffects {:event [:some-id :some-param]
 *              :db    <original contents of app-db>}
 *
 *  :effects   {:db        <new value for app-db>
 *              :dispatch  [:an-event-id :param1]}
 *
 *  :queue     [a collection of further interceptors]
 *
 *  :stack     [a collection of interceptors already walked]}
 */
fun dbHandlerToInterceptor(
    handlerFn: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
): Map<Interceptor, Any> = toInterceptor(
    id = ":db-handler",
    before = { context: Map<Context, Any> ->
        val cofx = context[Coeffects] as Map<*, *>
        val oldDb = cofx[Db] as MainViewModel
        val event = cofx[Event] as ArrayList<Any>

        val newDb = handlerFn(oldDb, event)

        val fx = context[Effects] as Map<*, *>

        val newContext = fx.plus(Db to newDb).let { newFx ->
            context.plus(Effects to newFx)
        }

        newContext
    }
)

val doFx: Map<Interceptor, Any> = toInterceptor(
    id = ":do-fx",
    after = { context: Map<Context, Any> ->
        val effects = context[Effects] as Map<*, *>
        val effectsWithoutDb = effects.minus(Db)

        val newDb = effects[Db] as MainViewModel?
        if (newDb != null) {
            val fxFn = fxHandlers[Db] as (value: Any) -> Unit
            Log.i(
                "doFx",
                "$newDb"
            )
            fxFn(newDb)
        }

        for ((effectKey, effectValue) in effectsWithoutDb) {
            val fxFn = fxHandlers[effectKey] as ((value: Any) -> Unit)?

            if (fxFn != null && effectValue != null)
                fxFn(effectValue)
            else
                Log.i(
                    "re-compose",
                    "no handler registered for effect: $effectKey. Ignoring."
                )
        }
    }
)

fun injectCofx(id: Any): Any {
    return toInterceptor(
        id = Coeffects,
        before = { context ->
            val handler = cofxHandlers[id] as ((Any) -> Any)?
            if (handler != null) {
                val newCofx = handler(context[Coeffects] ?: mapOf<Any, Any>())

                val newContext = context.plus(Coeffects to newCofx)
                newContext
            } else Log.e("injectCofx", "No cofx handler registered for $id")
        }
    )
}

val injectDb = injectCofx(id = Db)

/**
 * Register the given event `handler` (function) for the given `id`.
 */
fun regEventDb(
    id: Any,
    interceptors: ArrayList<Any>,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
    register(
        id = id,
        interceptors = arrayListOf(
            injectDb,
            doFx,
            interceptors,
            dbHandlerToInterceptor(handler)
        )
    )
}

fun regEventDb(
    id: Any,
    handler: (vm: MainViewModel, vec: ArrayList<Any>) -> MainViewModel
) {
    regEventDb(id, arrayListOf(), handler)
}

fun regFx(id: Any, handler: (value: Any) -> Unit) {
    Log.i("regFx", "$id")

    if (dbEvent[id] != null)
        Log.w("regFx: ", "overwriting handler for: $id")
    fxHandlers[id] = handler
}

fun regCofx(id: Any, handler: (coeffects: Map<Any, Any>) -> Map<Any, Any>) {
    Log.i("regCofx", "$id")

    if (cofxHandlers[id] != null)
        Log.w("regCofx: ", "overwriting handler for: $id")

    cofxHandlers[id] = handler
}

fun regSub(
    queryId: Any,
    computationFn: (vm: MainViewModel, queryVec: ArrayList<Any>) -> Any,
) {
    queryFns[queryId] = computationFn
}

fun regSub(
    queryId: Any,
    inputFn: (queryVec: ArrayList<Any>) -> Any,
    computationFn: (input: Any, queryVec: ArrayList<Any>) -> Any,
) {
    queryFns[queryId] = arrayOf(inputFn, computationFn)
}

class Framework : ViewModel() {
    init {
        regCofx(id = Db) { coeffects ->
            coeffects.plus(Db to appDb)
        }

        regFx(id = Db) { value ->
            if (appDb == (value as MainViewModel)) return@regFx

            Log.i("update appState", "$value")
            appDb = value
        }
    }

    private fun freshContext(event: Any) = mapOf(
        Coeffects to mapOf<Any, Any>(Event to event),
        Effects to mapOf()
    )

    private val receiver: Disposable =
        subscribe<ArrayList<Any>>().subscribe { eventVec ->
            if (eventVec.isEmpty()) return@subscribe

            val eventId = eventVec[0]

            viewModelScope.launch {
                val eventHandler: Any? = dbEvent[eventId]

                if (eventHandler != null) {
                    val interceptors = eventHandler
                            as List<Map<Interceptor, Any>>

                    val context1 =
                        interceptors.fold(freshContext(eventVec)) { context, interceptor ->
                            Log.i("interceptors:Before", "from $context")
                            val beforeFn = interceptor[Before]
                                    as (Map<Context, Any>) -> Any
                            val newContext = beforeFn(context)

                            newContext as Map<Context, Map<Any, Any>>
                        }

                    // TODO: Optimize this using stack and queue
                    interceptors.foldRight(context1) { interceptor, context ->
                        Log.i("interceptors:After", "from $context")
                        val afterFn = interceptor[After]
                                as (Map<Context, Any>) -> Any

                        val newContext = afterFn(context)
                        if (newContext is Map<*, *>)
                            newContext as Map<Context, Map<Any, Any>>
                        else context1
                    }

                    return@launch
                }

                val eventFx: Any? = fxEvent[eventId]

                if (eventFx != null) {
                    val function = eventFx
                            as (Map<Any, Any>, ArrayList<Any>) -> Map<Any, Any>

                    val fxMap = function(mapOf(), eventVec)
                    val fxVec = fxMap[":fx"] as ArrayList<ArrayList<Any>>
                    fxVec.forEach { effectVec ->
                        val id = effectVec[0]
                        val value = effectVec[1]
                        val fx = fxHandlers[id] as (value: Any) -> Unit
                        fx(value)
                    }

                    return@launch
                } else throw IllegalArgumentException(
                    "Event handler not found for id: $eventId"
                )
            }
        }

    fun halt() {
        Log.i("halt: ", "event receiver")
        receiver.dispose()
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("onCleared", "Resources released")
        halt()
    }
}

val publisher: PublishSubject<Any> = PublishSubject.create()

inline fun <reified T> subscribe(): Observable<T> = publisher.filter {
    it is T
}.map {
    it as T
}

fun dispatch(event: Any) {
    Log.i("dispatch", "$event")
    publisher.onNext(event)
}

@Composable
fun DispatchOnce(event: Any) {
    LaunchedEffect(true) {
        dispatch(event)
    }
}

fun event(id: Any, vararg args: Any) = arrayListOf(
    id,
    *args
)

