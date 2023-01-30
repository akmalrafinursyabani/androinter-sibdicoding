package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R

class NameEditText : AppCompatEditText {

    private lateinit var userIcon: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, deffStyleAttr: Int) : super(
        context,
        attributeSet,
        deffStyleAttr
    ) {
        init()
    }

    private fun init() {
        userIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_person_24) as Drawable
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        showIcon()
        hint = "Masukkan nama lengkap Anda"
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        compoundDrawablePadding = 24
    }


    private fun showIcon() {
        setIconDrawables(start = userIcon)
    }

    private fun setIconDrawables(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }
}