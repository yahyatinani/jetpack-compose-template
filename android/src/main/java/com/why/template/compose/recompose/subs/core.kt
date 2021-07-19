package com.why.template.compose.recompose.subs

import android.util.Log
import com.why.template.compose.recompose.db.MainViewModel
import com.why.template.compose.recompose.db.appState
import com.why.template.compose.recompose.memSubComp
import com.why.template.compose.recompose.queryFns

fun <T> subscribe(qvec: ArrayList<Any>): T {
    val id = qvec[0]

    return when (val r = queryFns[id]) {
        null -> throw IllegalArgumentException(
            "No query function was found for the given id: `$id`"
        )
        is Array<*> -> {
            val inputFn = r[0] as (ArrayList<Any>) -> Any
            val computationFn = r[1] as (Any, ArrayList<Any>) -> Any

            // TODO: Implement input with [v1 v2] return
            val input = inputFn(qvec)
            val cache = memSubComp[input]

            if (cache == null) {
                Log.i("input", "$input")
                val computation = computationFn(input, qvec)

                memSubComp[input] = computation
                computation as T
            } else {
                Log.i("cache", "$cache")
                cache as T
            }
        }
        else -> {
            val function = r as (MainViewModel, ArrayList<Any>) -> Any
            function(appState, qvec) as T
        }
    }
}
