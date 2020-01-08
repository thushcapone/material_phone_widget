package com.thushcapone.material_phone_widget

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.thushcapone.material_edit_text.MaterialEditText
import com.thushcapone.material_phone_widget.extensions.*
import java.lang.ref.WeakReference


/**
 * Created by thushcapone on 12/12/18.
 */
class PhoneWatcher(editText: MaterialEditText, private var mCountryCode: String) : TextWatcher {

    private var mEditText: WeakReference<MaterialEditText>? = null
    private var mCallback: PhoneWidget.OnValidPhoneListener? = null
    private var phoneFormatted: String? = null
    private var mNumberLength: Int = -1

    init {
        mEditText = WeakReference(editText)
        phoneFormatted = ""
        editText.edit?.removeTextChangedListener(sLastInstance)
        editText.edit?.addTextChangedListener(this)
        mNumberLength = mCountryCode.getPhoneNumberLength()
        editText.edit?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(mNumberLength))
        sLastInstance = this
    }

    fun setCountryCode(countryCode: String){
        mCountryCode = countryCode
        mNumberLength = mCountryCode.getPhoneNumberLength()
        mEditText?.get()?.edit?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(mNumberLength))
    }


    fun setCallback(callback: PhoneWidget.OnValidPhoneListener) {
        this.mCallback = callback
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @Synchronized
    override fun afterTextChanged(s: Editable) {
        when {
            s.toString().isValidPhoneNumber(mCountryCode) && phoneFormatted notEquals s.toString() -> {
                phoneFormatted = s.toString().getNationalNumberFormatted(mCountryCode)
                phoneFormatted?.let { mEditText?.get()?.edit?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(it.length)) }
                mCallback?.value(s.toString().getNormalizedNumber(mCountryCode))
            }
            phoneFormatted equals s.toString() -> phoneFormatted = ""
            s.toString().trim { it <= ' ' }.length == mNumberLength && s.toString().isInValidPhoneNumber(mCountryCode) ->
                mEditText?.get()?.layout?.error = mEditText?.get()?.context?.getString(R.string.error_phone_not_valid)
        }
    }

    companion object {

        private var sLastInstance: PhoneWatcher? = null
    }
}
