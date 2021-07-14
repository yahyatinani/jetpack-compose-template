package com.why.template.compose.event

import com.google.common.eventbus.EventBus

val eventBus by lazy { EventBus() }

fun dispatch(event: Any) {
    eventBus.post(event)
}
