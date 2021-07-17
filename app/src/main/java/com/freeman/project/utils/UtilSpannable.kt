package com.freeman.project.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View


object UtilSpannable {

    @Synchronized
    fun spannableFormatLink(formatString: String, linkString: String, onClickListener: View.OnClickListener): SpannableString {
        return createSpannableString(object: ClickableSpan(){

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true   //畫下引線
                //super.updateDrawState(ds)
            }

            override fun onClick(widget: View) {
                onClickListener.onClick(widget)
            }
        },formatString,linkString)
    }

    @Synchronized
    private fun createSpannableString(
        what: Any,
        formatString: String,
        vararg strings: String
    ): SpannableString {
        val spannableString = SpannableString(String.format(formatString, *strings))
        for (s in strings) {
            if (spannableString.contains(s)) {
                val start = spannableString.indexOf(s)
                val end = start + s.length
                spannableString.setSpan(
                    what,
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }
}