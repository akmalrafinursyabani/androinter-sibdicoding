package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R

class PasswordEditText : AppCompatEditText {

    private lateinit var passwordIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        showIcon()
        hint = "*******"
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        compoundDrawablePadding = 24
    }

    private fun init() {
        passwordIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_vpn_key_24) as Drawable
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                error = if (p0?.length!! < 6) {
                    "Password tidak boleh kurang dari 6 huruf!"
                } else {
                    null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun showIcon() {
        setButtonDrawables(start = passwordIcon)
    }

    private fun setButtonDrawables(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            start, top, end, bottom
        )
    }
}