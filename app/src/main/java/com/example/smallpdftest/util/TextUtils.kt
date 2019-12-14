package com.example.smallpdftest.util

import android.content.Context
import android.widget.TextView
import com.example.smallpdftest.R

class TextUtils {
    companion object {
        fun <T> setTextToTextView(context: Context, view: TextView, value : T) {
            if (value == null || value.toString() == "") {
                view.text = context.getString(R.string.not_specified)
            } else {
                view.text = value.toString()
            }
        }
    }
}