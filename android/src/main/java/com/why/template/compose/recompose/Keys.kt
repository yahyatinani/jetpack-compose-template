package com.why.template.compose.recompose

@Suppress("EnumEntryName")
enum class Keys {
    effects,
    coeffects,
    db,
    event,
    originalEvent,
    queue,
    stack,
    fx,
    dispatch,
    dispatchN,
    dofx,
    id,
    before,
    after;

    override fun toString(): String {
        return ":${super.toString()}"
    }
}
