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

/**
 * Created by Thiependa Seye on 1/8/19.
 * T.C
 * thiependa.seye@gmail.com
 */

infix fun String?.notEquals(other : String?) = run {
    !this.equals(other, ignoreCase = true)
}

infix fun String?.equals(other : String?) = run {
    this.equals(other, ignoreCase = true)
}