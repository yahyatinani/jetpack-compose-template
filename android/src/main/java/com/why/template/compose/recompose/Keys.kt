package com.why.template.compose.recompose

enum class Keys {
    effects,
    coeffects,
    db,
    event,
    fx,
    dispatch,
    dispatchN,
    dofx;

    override fun toString(): String {
        return ":${super.toString()}"
    }
}