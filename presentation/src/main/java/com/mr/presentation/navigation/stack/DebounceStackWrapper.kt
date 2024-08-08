package com.mr.presentation.navigation.stack

import com.mr.domain.navigation.debounce
import com.mr.domain.navigation.debounceForResult

class DebounceStackWrapper<Item, Event>(
    private val parent: Stack<Item, Event>
) : StackWrapper<Item, Event>(parent) {
    companion object {
        const val DEBOUNCE_KEY = "navigation"
    }

    private fun Item?.getSimpleItem(): String = this.toString().substringBefore("@")

    override fun pop(): Boolean = debounceForResult(DEBOUNCE_KEY) { super.pop() }
        .fold({ result }, { false })

    override fun popAll() = debounce(DEBOUNCE_KEY) { super.popAll() }

    override fun plusAssign(items: List<Item>) = debounce(DEBOUNCE_KEY) { super.plusAssign(items) }

    override fun plusAssign(item: Item) = debounce(DEBOUNCE_KEY) { super.plusAssign(item) }

    override fun popUntil(predicate: (Item) -> Boolean): Boolean =
        debounceForResult(DEBOUNCE_KEY) { super.popUntil(predicate) }
            .fold({ result }, { false })

    override fun replaceAll(items: List<Item>) = debounce(DEBOUNCE_KEY) {
        super.replaceAll(items)
    }

    override fun replaceAll(item: Item) = debounce(DEBOUNCE_KEY) {
        if (!(!parent.canPop && parent.lastItemOrNull.getSimpleItem() == item.getSimpleItem())) super.replaceAll(
            item
        )
    }

    override fun replace(item: Item) = debounce(DEBOUNCE_KEY) { super.replace(item) }

    override fun push(items: List<Item>) = debounce(DEBOUNCE_KEY) { super.push(items) }

    override fun push(item: Item) = debounce(DEBOUNCE_KEY) {
        if (parent.lastItemOrNull.getSimpleItem() != item.getSimpleItem()) super.push(item)
    }
}