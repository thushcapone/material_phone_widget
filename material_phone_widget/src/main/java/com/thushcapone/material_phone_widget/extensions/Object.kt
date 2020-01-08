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

import android.util.Log


/**
 * Created by Thiependa Seye on 1/8/19.
 * T.C.
 * thiependa.seye@gmail.com
 */

/**
 * Return the value after computing or a default value when encountered an exception
 */
inline fun <reified T: Any> tryOrDefault(defaultValue: T, f: () -> T): T = run {
    return try {
        f()
    } catch (e: Exception) {
        Log.d("PhoneWidget", e.message)
        defaultValue
    }
}