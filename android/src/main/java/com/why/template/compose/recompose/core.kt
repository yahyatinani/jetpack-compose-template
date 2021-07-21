package com.why.template.compose.recompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.why.template.compose.recompose.Context.*
import com.why.template.compose.recompose.cofx.injectDb
import com.why.template.compose.recompose.events.handle
import com.why.template.compose.recompose.events.register
import com.why.template.compose.recompose.fx.doFx
import com.why.template.compose.recompose.registrar.*
import com.why.template.compose.recompose.stdinterceptors.dbHandlerToInterceptor
import com.why.template.compose.recompose.stdinterceptors.fxHandlerToInterceptor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch

enum class Context {
    Effects,
    Coeffects,
    Db,
    Event
}

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

/*
-- Events ---------------------------------------------------
 */
/**
 * Register the given event `handler` (function) for the given `id`.
 */
inline fun <reified T> regEventDb(
    id: Any,
    interceptors: ArrayList<Any>,
    crossinline handler: (db: T?, vec: ArrayList<Any>) -> T
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

inline fun <reified T> regEventDb(
    id: Any,
    crossinline handler: (db: T?, vec: ArrayList<Any>) -> T
) {
    Log.i("regEventDb", "$id")
    regEventDb(id, arrayListOf(), handler)
}

fun regEventFx(
    id: Any,
    interceptors: ArrayList<Any>,
    handler: (cofx: Map<Any, Any>, event: ArrayList<Any>) -> Map<Any, Any>
) {
    register(
        id = id,
        interceptors = arrayListOf(
            injectDb,
            doFx,
            interceptors,
            fxHandlerToInterceptor(handler)
        )
    )
}

fun regEventFx(
    id: Any,
    handler: (cofx: Map<Any, Any>, event: ArrayList<Any>) -> Map<Any, Any>
) {
    regEventFx(id, arrayListOf(), handler)
}

/*
-- subscriptions ---------------------
 */
fun <T> regSub(
    queryId: Any,
    computationFn: (db: T, queryVec: ArrayList<Any>) -> Any,
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

fun <T> subscribe(qvec: ArrayList<Any>): T {
    return com.why.template.compose.recompose.subs.subscribe<T>(qvec)
}
/*
-- effects ---------------------------------
 */

/**
 * Register the given effect `handler` for the given `id`
 * @param id
 * @param handler is a side-effecting function which takes a single argument
 * and whose return value is ignored.
 */
fun regFx(id: Any, handler: (value: Any) -> Unit) {
    com.why.template.compose.recompose.fx.regFx(id, handler)
}

/*
-- Framework ------------------------------
 */

class Framework : ViewModel() {
    private val receiver = subscribe<ArrayList<Any>>().subscribe { eventVec ->
        if (eventVec.isEmpty()) return@subscribe

        viewModelScope.launch {
            handle(eventVec)
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

private inline fun <reified T> subscribe(): Observable<T> = publisher.filter {
    it is T
}.map {
    it as T
}

fun dispatch(event: Any) {
    Log.i("dispatch", "$event")
    publisher.onNext(event)
}

fun dispatchSync(event: ArrayList<Any>) {
    Log.i("dispatchSync", "$event")
    handle(event)
}

@Composable
fun DispatchOnce(event: Any) {
    LaunchedEffect(true) {
        dispatch(event)
    }
}
