package com.bnet.sarvesuraksha.utils

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.InputType
import android.text.Spanned
import android.widget.EditText


class PanCardInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val input = (dest?.subSequence(0, dstart).toString() + source?.subSequence(start, end) + dest?.subSequence(dend, dest.length)).toUpperCase()

        if (input.length > 10) return ""

        // Validate the format: first 5 letters, next 4 digits, and last letter
        if (input.length <= 5 && input.any { !it.isLetter() }) return ""
        if (input.length in 6..9 && input.any { !it.isDigit() }) return ""
        if (input.length == 10 && !input.last().isLetter()) return ""

        return null
    }
}


