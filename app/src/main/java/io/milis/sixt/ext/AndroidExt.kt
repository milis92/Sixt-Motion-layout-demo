package io.milis.sixt.ext

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.milis.sixt.R

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun RecyclerView.withDividers(@DrawableRes drawable: Int = R.drawable.ic_divider) {
    val itemDecorator = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    itemDecorator.setDrawable(ContextCompat.getDrawable(this.context, drawable)!!)
    this.addItemDecoration(itemDecorator)
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
}
