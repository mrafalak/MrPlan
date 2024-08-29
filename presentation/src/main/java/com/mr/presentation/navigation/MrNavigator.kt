package com.mr.presentation.navigation

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import com.mr.presentation.navigation.stack.DebounceStackWrapper
import com.mr.presentation.navigation.stack.Stack
import com.mr.presentation.navigation.stack.VoyagerNavigationStack
import com.mr.presentation.navigation.stack.WatcherWrapper

interface MrNavigator : Stack<Screen, StackEvent> {
    val noDebounce: Stack<Screen, StackEvent>
    val forwardCallback: NavigationCallbackWatcher
    val backwardCallback: NavigationCallbackWatcher
}

internal class NavigatorImpl(
    val navigator: Navigator,

    private val stack: Stack<Screen, StackEvent> =
        VoyagerNavigationStack(navigator),

    override val forwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher(),
    override val backwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher(),

    override val noDebounce: Stack<Screen, StackEvent> =
        WatcherWrapper(stack, forwardCallback, backwardCallback),

    private val debounceStack: Stack<Screen, StackEvent> =
        DebounceStackWrapper(noDebounce)
) : MrNavigator,
    Stack<Screen, StackEvent> by debounceStack

class NavigatorNoOp(
    override val noDebounce: Stack<Screen, StackEvent> =
        Stack.NoOp(lastEvent = StackEvent.Idle),
    override val forwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher(),
    override val backwardCallback: NavigationCallbackWatcher = NavigationCallbackWatcher(),
) : MrNavigator, Stack<Screen, StackEvent> by noDebounce