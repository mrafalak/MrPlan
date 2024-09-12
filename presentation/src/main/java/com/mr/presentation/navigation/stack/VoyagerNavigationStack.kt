package com.mr.presentation.navigation.stack

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator

class VoyagerNavigationStack(val navigator: Navigator): Stack<Screen, StackEvent> {
    override val items: List<Screen>
        get() = navigator.items
    override val lastEvent: StackEvent
        get() = navigator.lastEvent
    override val lastItemOrNull: Screen?
        get() = navigator.lastItemOrNull
    override val size: Int
        get() = navigator.size
    override val isEmpty: Boolean
        get() = navigator.isEmpty
    override val canPop: Boolean
        get() = navigator.canPop

    override fun pop(): Boolean = navigator.pop()
    override fun popAll() = navigator.popAll()
    override fun clearEvent() = navigator.clearEvent()
    override fun plusAssign(items: List<Screen>) = navigator.plusAssign(items)
    override fun plusAssign(item: Screen) = navigator.plusAssign(item)
    override fun popUntil(predicate: (Screen) -> Boolean): Boolean = navigator.popUntil(predicate)
    override fun replaceAll(items: List<Screen>) = navigator.replaceAll(items)
    override fun replaceAll(item: Screen) = navigator.replaceAll(item)
    override fun replace(item: Screen) = navigator.replace(item)
    override fun push(items: List<Screen>) = navigator.push(items)
    override fun push(item: Screen) = navigator.push(item)
}