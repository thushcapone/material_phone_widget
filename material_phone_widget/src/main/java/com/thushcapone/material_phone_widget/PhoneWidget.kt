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

package com.thushcapone.material_phone_widget

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.rilixtech.widget.countrycodepicker.Country
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import com.thushcapone.material_edit_text.MaterialEditText
import com.thushcapone.material_phone_widget.extensions.*
import java.util.*


/**
 * Created by Thiependa Seye on 1/8/19.
 * T.C.
 * thiependa.seye@gmail.com
 */
class PhoneWidget : LinearLayout {

    var editPhone: MaterialEditText? = null

    var countryCodePicker: CountryCodePicker? = null

    private var mPhoneWatcher: PhoneWatcher? = null

    private var mHint: String? = null

    var countryCode: String = ""
        set(value){
            field = value
            value.let { mPhoneWatcher?.setCountryCode(it)}
            refreshCountryPickerPadding()
        }

    var number: String
        get() = editPhone?.edit?.text!!.toString().getNormalizedNumber(countryCode)
        set(phone) = if(phone.isValidPhoneNumber()) {
            editPhone?.edit?.run {
                setText(phone)
                setSelection(phone.length)
            } ?: Unit
        } else {
            Toast.makeText(this.context, R.string.error_phone_not_valid, Toast.LENGTH_SHORT).show()
        }

    val textView: View?
        get() = this.editPhone

    constructor(context: Context) : super(context) {
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getStyleable(attrs)
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        getStyleable(attrs)
        initializeViews(context)
    }

    private fun getStyleable(attrs: AttributeSet){
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PhoneWidget, 0, 0
        )
        mHint = context.getString(a.getResourceId(R.styleable.PhoneWidget_hint, R.string.hint_phone_number))
        countryCode = a.getString(R.styleable.PhoneWidget_defaultCountry) ?: "SN"
        a.recycle()
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private fun initializeViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.compound_phone_view, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setupView()
        setupHint()
        setupCountryPicker()
        setCountryPickerPadding()
        setCountryPickerWatcher()
    }

    private fun setupView() {
        editPhone = findViewById(R.id.edit_phone)
        countryCodePicker = findViewById(R.id.country_picker)
    }

    private fun setupHint(){
        editPhone?.layout?.hint = mHint
    }

    private fun setupCountryPicker(){
        countryCodePicker?.enableHint(false)
        countryCodePicker?.registerPhoneNumberTextView(editPhone?.edit)
        countryCodePicker?.setDefaultCountryUsingNameCode(countryCode)
        countryCodePicker?.setCountryForNameCode(countryCode)
        countryCodePicker?.setOnCountryChangeListener {
            editPhone?.edit?.clear()
            countryCode = it.iso.toUpperCase(Locale.getDefault())
        }
    }

    private fun setCountryPickerWatcher() {
        mPhoneWatcher = editPhone?.let { PhoneWatcher(it, countryCode) }
    }

    private fun setCountryPickerPadding(){
        countryCodePicker?.runOnLayoutDone { cpp ->
            editPhone?.edit?.runOnLayoutDone {
                val paddingNormal = context.resources.getDimension(R.dimen.spacing_normal).toInt()
                it.setPadding(cpp.width, paddingNormal, paddingNormal, paddingNormal)
            }
        }
    }

    private fun refreshCountryPickerPadding(){
        Handler().postDelayed({ setCountryPickerPadding() }, 100)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()

        bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState())
        bundle.putString(STATE_PHONE, editPhone?.edit?.text.toString())

        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {

            super.onRestoreInstanceState(state.getParcelable(STATE_SUPER_CLASS))
            editPhone?.edit?.setText(state.getString(STATE_PHONE))
        } else
            super.onRestoreInstanceState(state)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        // Makes sure that the state of the child views in the side
        // spinner are not saved since we handle the state in the
        // onSaveInstanceState.
        super.dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        // Makes sure that the state of the child views in the side
        // spinner are not restored since we handle the state in the
        // onSaveInstanceState.
        super.dispatchThawSelfOnly(container)
    }

    fun removePhone() {
        editPhone?.edit?.setText("")
    }

    fun setHint(hint: CharSequence) {
        editPhone?.layout?.hint = hint
    }

    fun setOnValidPhoneListener(listener: OnValidPhoneListener) {
        mPhoneWatcher?.setCallback(listener)
    }

    interface OnCountryCodeListener {
        fun value(countryCode: String)
    }

    interface OnValidPhoneListener {

        fun value(validPhone: String)

    }

    companion object {

        /**
         * Identifier for the state to save the selected country code.
         */
        private val STATE_COUNTRY_CODE = "country code"
        /**
         * Identifier for the state to save the typed phone.
         */
        private val STATE_PHONE = "phone"
        /**
         * Identifier for the state of the super class.
         */
        private val STATE_SUPER_CLASS = "SuperClass"
    }

}
