package com.olvr.maskplate

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText

object TextWatcherPlate {

    val REGEX_OLD = "(^[A-Z]{3}-?\\d{4}?)$".toRegex()
    val REGEX_NEW = "(^[A-Z]{3}-?\\d[A-Z]\\d{2})$".toRegex()
    val REGEX = "(?:(^[A-Z]{3}-?\\d[A-Z]\\d{0,2}$)|(?:(^[A-Z]{3}-?\\d{1,4}$))|(?:(^[A-Z]{3}-?$))|(^[A-Z]{0,3}$))".toRegex()

    @JvmOverloads
    fun getWatcher(
            inputField: AppCompatEditText,
            separatorEnabled: Boolean = false
    ) : TextWatcher = object : TextWatcher {

        private var textCurrent: String = ""

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val textChanged: String = s.toString()
            if (textChanged == textCurrent) {
                return
            }

            inputField.removeTextChangedListener(this)
            textCurrent = mask(textChanged, before)
            inputField.setText(textCurrent)

            try {
                inputField.setSelection(textCurrent.count())
            } catch (ex: IndexOutOfBoundsException) {
                inputField.setSelection(textCurrent.count() - 1)
            }

            inputField.addTextChangedListener(this)
        }

        private fun mask(text: String, before: Int = 0) : String {
            if (REGEX.matches(text).not()) {
                return this.textCurrent
            }

            this.textCurrent = unmask(text)

            if (separatorEnabled.not()) {
                return this.textCurrent
            }

            var newText = ""
            this.textCurrent.forEachIndexed { index, char ->
                newText += char.toString()
                if (index == 2) newText += "-"
            }

            if (text.none { it.toString() == "-" } && text.count() < before) {
                newText = unmask(newText)
            }

            return newText
        }

    }

    @JvmOverloads
    fun isValid(plate: CharSequence, regex: Regex = REGEX) : Boolean {
        return plate.isNotBlank() && regex.matches(plate)
    }

    fun unmask(text: String) : String = text.replace("[-]".toRegex(), "")

}