package com.mr.domain.navigation

import com.mr.common.base.navigation.now
import timber.log.Timber

private val debounceMap = HashMap<String, Long>()

sealed class DebounceResult<T> {
    data class Success<T>(val result: T) : DebounceResult<T>()
    data class Ignored<T>(val invokedAt: Long) : DebounceResult<T>()

    fun <Y> fold(onSuccess: Success<T>.() -> Y, onIgnored: Ignored<T>.() -> Y): Y = when (this) {
        is Success -> onSuccess(this)
        is Ignored -> onIgnored(this)
    }
}

fun <T> debounceForResult(key: String, time: Long = 500, body: () -> T): DebounceResult<T> {
    val lastInvokedAt = debounceMap[key] ?: 0L
    return if ((lastInvokedAt + time) < now()) {
        debounceMap[key] = now()
        DebounceResult.Success(body())
    } else {
        Timber.tag("debounceForResult").w("Action ignored, debounce: $key")
        DebounceResult.Ignored(lastInvokedAt)
    }
}

fun debounce(key: String, time: Long = UiConfig.NAVIGATION_ANIM_DURATION.toLong(), body: () -> Unit) {
    debounceForResult(key, time, body)
}