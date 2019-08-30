package io.milis.sixt.util

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.ShapeAppearanceModel
import io.milis.sixt.R

class ExpandableFab @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        calculateRadius()
    }

    private fun calculateRadius() {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                radius = ((height / 2) - 2 * 2).toFloat()
            }
        })
    }


}