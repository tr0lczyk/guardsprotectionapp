package com.example.guardsprotectionapp.utils

import androidx.core.view.postDelayed
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import com.google.android.material.textfield.TextInputLayout

class SmartTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val scrollView by lazy(LazyThreadSafetyMode.NONE) {
        findParentOfType<ScrollView>() ?: findParentOfType<NestedScrollView>()
    }

    private fun scrollIfNeeded() {
        // Wait a bit (like 10 frames) for other UI changes to happen
        scrollView?.postDelayed(160) {
            scrollView?.scrollDownTo(this)
        }
    }

    override fun setError(value: CharSequence?) {
        val changed = error != value

        super.setError(value)

        // work around https://stackoverflow.com/q/34242902/1916449
        if (value == null) isErrorEnabled = false

        // work around https://stackoverflow.com/q/31047449/1916449
        if (changed) scrollIfNeeded()
    }
}

/**
 * Find the closest ancestor of the given type.
 */
inline fun <reified T> View.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) p = p.parent
    return p as T?
}

/**
 * Scroll down the minimum needed amount to show [descendant] in full. More
 * precisely, reveal its bottom.
 */
fun ViewGroup.scrollDownTo(descendant: View) {
    // Could use smoothScrollBy, but it sometimes over-scrolled a lot
    howFarDownIs(descendant)?.let { scrollBy(0, it) }
}

/**
 * Calculate how many pixels below the visible portion of this [ViewGroup] is the
 * bottom of [descendant].
 *
 * In other words, how much you need to scroll down, to make [descendant]'s bottom
 * visible.
 */
fun ViewGroup.howFarDownIs(descendant: View): Int? {
    val bottom = Rect().also {
        // See https://stackoverflow.com/a/36740277/1916449
        descendant.getDrawingRect(it)
        offsetDescendantRectToMyCoords(descendant, it)
    }.bottom
    return (bottom - height - scrollY).takeIf { it > 0 }
}