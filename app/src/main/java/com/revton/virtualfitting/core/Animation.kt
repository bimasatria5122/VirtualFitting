package com.revton.virtualfitting.core

import android.view.View
import android.view.animation.LinearInterpolator

class Animation {

    public fun buttonClicked(id:View) // Button animation opacity when clicked @param(viewid)
    {
        id.alpha = 0.4f
        id.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            startDelay = 1000
            start()
        }
    }


}