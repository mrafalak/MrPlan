package com.mr.presentation.navigation.stack

interface Stack<Item, Event> {
    val items: List<Item>
    val lastEvent: Event
    val lastItemOrNull: Item?
    val size: Int
    val isEmpty: Boolean
    val canPop: Boolean

    fun push(item: Item)
    fun push(items: List<Item>)
    fun replace(item: Item)
    fun replaceAll(item: Item)
    fun replaceAll(items: List<Item>)
    fun pop(): Boolean
    fun popAll()
    fun popUntil(predicate: (Item) -> Boolean): Boolean
    fun plusAssign(item: Item)
    fun plusAssign(items: List<Item>)
    fun clearEvent()

    class NoOp<Item, Event>(
        override val items: List<Item> = emptyList(),
        override val lastItemOrNull: Item? = null,
        override val lastEvent: Event,
        override val size: Int = 0,
        override val isEmpty: Boolean = true,
        override val canPop: Boolean = false
    ) : Stack<Item, Event> {
        override fun pop(): Boolean = false
        override fun popAll() = Unit
        override fun clearEvent() = Unit
        override fun plusAssign(items: List<Item>) = Unit
        override fun plusAssign(item: Item) = Unit
        override fun popUntil(predicate: (Item) -> Boolean): Boolean = false
        override fun replaceAll(items: List<Item>) = Unit
        override fun replaceAll(item: Item) = Unit
        override fun replace(item: Item) = Unit
        override fun push(items: List<Item>) = Unit
        override fun push(item: Item) = Unit
    }
}