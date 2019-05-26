package com.olvr.maskplate

import android.content.Context
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import com.google.android.material.textfield.TextInputEditText

class TextInputPlateEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    private var maskEnabled: Boolean = false
    private var plateWatcher: TextWatcher = TextWatcherPlate.getWatcher(this)

    init {
        this.filters = arrayOf(InputFilter.AllCaps())
        this.context.withStyledAttributes(
                set = attrs,
                attrs = R.styleable.TextInputPlateEditText) {
            maskEnabled =
                    getBoolean(R.styleable.TextInputPlateEditText_maskEnabled, false)
        }

        enableMask(maskEnabled)
    }

    fun enableMask(isEnabled: Boolean) {
        this.removeTextChangedListener(plateWatcher)
        this.maskEnabled = isEnabled
        this.plateWatcher = TextWatcherPlate.getWatcher(this, maskEnabled)
        this.addTextChangedListener(plateWatcher)
    }

    fun getTextPlate() : String = if (text.isNullOrBlank()) "" else text.toString()

    fun getTextPlateWithoutMask() : String = TextWatcherPlate.unmask(getTextPlate())

    fun setTextPlate(@StringRes plate: Int) = setTextPlate(context.getString(plate))

    fun setTextPlate(plate: String) {
        if (TextWatcherPlate.isValid(plate)) {
            setText(plate)
        }
    }

    fun isRegistrationPlateValid() : Boolean =
            isNewPatternRegistrationPlate() || isOldPatternRegistrationPlate()

    fun isNewPatternRegistrationPlate() : Boolean =
            TextWatcherPlate.isValid(getTextPlate(), TextWatcherPlate.REGEX_NEW)

    fun isOldPatternRegistrationPlate() : Boolean =
            TextWatcherPlate.isValid(getTextPlate(), TextWatcherPlate.REGEX_OLD)

}