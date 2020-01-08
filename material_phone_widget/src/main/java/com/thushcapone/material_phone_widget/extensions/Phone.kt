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

import com.google.i18n.phonenumbers.PhoneNumberUtil


/**
 * Created by Thiependa Seye on 1/8/19.
 * T.C.
 * thiependa.seye@gmail.com
 */
fun String.isValidPhoneNumber(countryCode: String = "SN") = tryOrDefault(false) {
    val phoneUtil = PhoneNumberUtil.getInstance()
    phoneUtil.isValidNumber(phoneUtil.parse(this, countryCode))
}

fun String.isInValidPhoneNumber(countryCode: String = "SN") : Boolean = let {
    !this.isValidPhoneNumber(countryCode)
}

/**
 * Returns a hidden number with only the last specified digit shown (by default = 3)
 * <p>Examples:
 * <blockquote><pre>
 * string = 77 747 18 81 ; hidePhoneNumber() returns "**  *** *8 81"
 * string = 77 747 18 81 ; hidePhoneNumber(5) returns "**  **7 18 81"
 * </pre></blockquote>
 *
 */
fun String.hidePhoneNumber(shown : Int = 3) :String = run {
    var res = ""
    var cpt = 0
    for (i in this.length -1 downTo 0){
        if(this[i].isWhitespace() || cpt < shown){
            res = "${this[i]}$res"
            cpt += if(this[i].isWhitespace()) 0 else 1
        }else{
            res = "*$res"
        }
    }
    res
}


/**
 * Returns normalized phone number
 * <p>Examples:
 * <blockquote><pre>
 * string = 777471881 ; getNormalizedNumber() returns "+221777471881"
 * string = 77 747 18 81 ; getNormalizedNumber() returns "+221777471881"
 * </pre></blockquote>
 *
 * @param countryCode
 * @return number String
 */
fun String?.getNormalizedNumber(countryCode: String = "SN") = tryOrDefault("") {
    val phoneUtil = PhoneNumberUtil.getInstance()
    phoneUtil.format(phoneUtil.parse(this, countryCode), PhoneNumberUtil.PhoneNumberFormat.E164);
}

/**
 * Returns national phone number
 * <p>Examples:
 * <blockquote><pre>
 * string = 777471881 ; getNationalNumberFormatted() returns "77 747 18 81"
 * string = +221777471881 ; getNationalNumberFormatted() returns "77 747 18 81"
 * </pre></blockquote>
 *
 * @param countryCode
 * @return number String
 */
fun String?.getNationalNumberFormatted(countryCode: String = "SN") = tryOrDefault("") {
    val phoneUtil = PhoneNumberUtil.getInstance()
    phoneUtil.format(phoneUtil.parse(this, countryCode), PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
}

//TODO Have to comment you my friend
fun String.getPhoneNumberLength() = tryOrDefault(-1){
    PhoneNumberUtil.getInstance().getExampleNumber(this).nationalNumber.toString().getNationalNumberFormatted(this).length
}
