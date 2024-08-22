package com.mr.presentation.navigation.stack

import com.mr.presentation.navigation.NavigationCallbackWatcher

class WatcherWrapper<Item, Event>(
    private val parent: Stack<Item, Event>,
    private val forwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher(),
    private val backwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher()
) : StackWrapper<Item, Event>(parent) {
    private fun Item?.getSimpleItem(): String = this.toString().substringBefore("@")

    override fun clearEvent() = forwardCallback.isAllowedNoResult { super.clearEvent() }

    override fun plusAssign(item: Item) = forwardCallback.isAllowedNoResult {
        super.plusAssign(item)
    }

    override fun plusAssign(items: List<Item>) = forwardCallback.isAllowedNoResult {
        super.plusAssign(items)
    }

    override fun pop(): Boolean {
        return backwardCallback.isAllowedForResult(body = {
            forwardCallback.isAllowedForResult(body = {
                super.pop()
            }, onCancelled = { false })
        }, onCancelled = { false })
    }

    override fun popAll() {
        backwardCallback.isAllowedNoResult {
            forwardCallback.isAllowedNoResult {
                super.popAll()
            }
        }
    }

    override fun popUntil(predicate: (Item) -> Boolean): Boolean {
        return backwardCallback.isAllowedForResult(body = {
            forwardCallback.isAllowedForResult(body = {
                super.popUntil(predicate)
            }, onCancelled = { false })
        }, onCancelled = { false })
    }

    override fun push(items: List<Item>) = forwardCallback.isAllowedNoResult {
        super.push(items)
    }

    override fun replace(item: Item) = forwardCallback.isAllowedNoResult {
        super.replace(item)
    }

    override fun replaceAll(item: Item) = forwardCallback.isAllowedNoResult {
        if (!(!parent.canPop && parent.lastItemOrNull.getSimpleItem() == item.getSimpleItem())) super.replaceAll(item)
    }

    override fun replaceAll(items: List<Item>) = forwardCallback.isAllowedNoResult {
        super.replaceAll(items)
    }

    override fun push(item: Item) = forwardCallback.isAllowedNoResult {
        if (parent.lastItemOrNull.getSimpleItem() != item.getSimpleItem()) super.push(item)
    }
}