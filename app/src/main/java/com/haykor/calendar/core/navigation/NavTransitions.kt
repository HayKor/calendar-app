package com.haykor.calendar.core.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.navigationevent.NavigationEvent

private object NavAnimationDefaults {
    const val DURATION = 450
    const val DELAY = 30
    const val SCALE = 0.92f
}

internal fun forwardTransition(): ContentTransform {
    val duration = NavAnimationDefaults.DURATION
    val easing = EaseOutCubic
    return slideInHorizontally(
        animationSpec = tween(duration, delayMillis = NavAnimationDefaults.DELAY, easing = easing),
    ) { it } togetherWith
        scaleOut(
            animationSpec = tween(duration, easing = easing),
            targetScale = NavAnimationDefaults.SCALE,
        ) + fadeOut(tween(duration, easing = easing))
}

internal fun backTransition(): ContentTransform {
    val duration = NavAnimationDefaults.DURATION
    val easing = EaseOutCubic
    return scaleIn(
        animationSpec = tween(duration, easing = easing),
        initialScale = NavAnimationDefaults.SCALE,
    ) + fadeIn(tween(duration, easing = easing)) togetherWith
        slideOutHorizontally(
            animationSpec = tween(duration, easing = easing),
        ) { it }
}

internal fun predictiveBackTransition(swipeEdge: Int): ContentTransform {
    val duration = NavAnimationDefaults.DURATION
    val easing = EaseOutCubic
    val slideDirection = if (swipeEdge == NavigationEvent.EDGE_LEFT) 1 else -1
    return scaleIn(
        animationSpec = tween(duration, easing = easing),
        initialScale = NavAnimationDefaults.SCALE,
    ) + fadeIn(tween(duration, easing = easing)) togetherWith
        slideOutHorizontally(
            animationSpec = tween(duration, easing = easing),
        ) { it * slideDirection }
}
