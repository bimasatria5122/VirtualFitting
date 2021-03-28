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
            startDelay = 200
            start()
        }
    }


    public fun buttonClickedScale(id:View) // Button animation opacity when clicked @param(viewid)
    {
        id.scaleX = 0.85f
        id.scaleY = 0.85f
        id.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            scaleX(1f)
            scaleY(1f)
            startDelay = 200
            start()
        }
    }


}