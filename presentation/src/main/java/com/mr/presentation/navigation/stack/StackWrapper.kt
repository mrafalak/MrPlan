package com.mr.presentation.navigation.stack

open class StackWrapper<Item, Event>(
    private val parentStack: Stack<Item, Event>
) : Stack<Item, Event> {
    override val items: List<Item>
        get() = parentStack.items
    override val lastEvent: Event
        get() = parentStack.lastEvent
    override val lastItemOrNull: Item?
        get() = parentStack.lastItemOrNull
    override val size: Int
        get() = parentStack.size
    override val isEmpty: Boolean
        get() = parentStack.isEmpty
    override val canPop: Boolean
        get() = parentStack.canPop

    override fun pop(): Boolean = parentStack.pop()
    override fun popAll() = parentStack.popAll()
    override fun clearEvent() = parentStack.clearEvent()
    override fun plusAssign(items: List<Item>) = parentStack.plusAssign(items)
    override fun plusAssign(item: Item) = parentStack.plusAssign(item)
    override fun popUntil(predicate: (Item) -> Boolean): Boolean = parentStack.popUntil(predicate)
    override fun replaceAll(items: List<Item>) = parentStack.replaceAll(items)
    override fun replaceAll(item: Item) = parentStack.replaceAll(item)
    override fun replace(item: Item) = parentStack.replace(item)
    override fun push(items: List<Item>) = parentStack.push(items)
    override fun push(item: Item) = parentStack.push(item)
}