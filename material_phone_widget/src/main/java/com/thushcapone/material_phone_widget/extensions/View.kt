/*
 *
 *  * Copyright (c) 2018. T.C.
 *  * All Rights Reserved
 *  *
 *  * This product is protected by copyright and distributed under
 *  * licenses restricting copying, distribution and decompilation.
 *  *
 *
 */

package com.thushcapone.material_phone_widget.extensions

import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.ViewCompat


/**
 * Created by Thiependa Seye on 1/8/19.
 * T.C.
 * thiependa.seye@gmail.com
 */


fun <T : View> T.runOnLayoutDone(action : (view : T) -> Unit) = apply {
    if(ViewCompat.isAttachedToWindow(this)){
        action(this)
    }else {
        val l = object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                viewTreeObserver.removeOnGlobalLayoutListener(this)
                action(this@runOnLayoutDone)
            }
        }
        this.viewTreeObserver.addOnGlobalLayoutListener(l)
    }
}
