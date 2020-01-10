package com.thushcapone.material_phone_widget

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.thushcapone.material_phone_widget.extensions.clear
import java.util.*

/**
 * Created by Thiependa Seye on 12/20/18.
 * T.C.
 * thiependa.seye@gmail.com
 */

@BindingAdapter("phoneNumberAttrChanged")
fun setListener(phone: PhoneWidget, listener: InverseBindingListener) {
    phone.editPhone?.edit?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            listener.onChange()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

@BindingAdapter("phoneNumber")
fun setTextValue(phone: PhoneWidget, value: String?) {
    if (value != phone.editPhone?.edit?.text.toString()) phone.editPhone?.edit?.setText(value)
}

@InverseBindingAdapter(attribute = "phoneNumber")
fun getTextValue(phone: PhoneWidget): String? {
    return phone.editPhone?.edit?.text.toString()
}

@BindingAdapter("countryCodeListener")
fun countryCodeListener(phone: PhoneWidget, listener: PhoneWidget.OnCountryCodeListener){
    phone.countryCodePicker?.setOnCountryChangeListener {
        listener.value(it.iso.toUpperCase(Locale.getDefault()))
        phone.editPhone?.edit?.clear()
        phone.countryCode = it.iso.toUpperCase(Locale.getDefault())
    }

}
