package com.mr.presentation.navigation

import java.util.UUID

class NavigationCallbackWatcher {
    private var callbacks = mapOf<UUID, NavigationContext.() -> Boolean>()

    fun addCallback(uuid: UUID, body: NavigationContext.() -> Boolean) {
        removeCallback(uuid, body)
        callbacks = callbacks.toMutableMap().apply {
            this[uuid] = body
        }
    }

    fun removeCallback(uuid: UUID, body: NavigationContext.() -> Boolean) {
        callbacks = callbacks.toMutableMap()
            .apply { remove(uuid) }
            .filter { it.value != body }
    }

    fun <T> isAllowedForResult(body: () -> T, onCancelled: () -> T): T {
        val callbacks = callbacks.values.toList()
        val context = NavigationContext { body() }
        return if (callbacks.all { it(context) }) {
            body()
        } else {
            onCancelled()
        }
    }

    fun <T> isAllowed(body: () -> T): T? = isAllowedForResult(body, onCancelled = { null })
    fun isAllowedNoResult(body: () -> Unit) = isAllowed(body) ?: Unit


    data class NavigationContext(private val continuation: () -> Unit) {
        fun continueNavigation() {
            continuation()
        }
    }
}