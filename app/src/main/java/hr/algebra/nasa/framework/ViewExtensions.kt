package hr.algebra.nasa.framework

import android.view.View
import android.view.animation.AnimationUtils

fun View.applyAnimation(resourceId: Int) =
    startAnimation(AnimationUtils.loadAnimation(context, resourceId))