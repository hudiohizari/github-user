package id.hudiohizari.githubuser.util.extention

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import id.hudiohizari.githubuser.R

fun View.snackbar(message: String?) {
    try {
        Snackbar.make(
            this,
            message ?: context.getString(R.string.emptyText),
            Snackbar.LENGTH_LONG
        ).show()
    } catch (e: Exception) {
        Log.e("snackbar", "${e.message}")
    }
}

fun View.snackbarLong(message: String?) {
    try {
        Snackbar.make(
            this,
            message ?: context.getString(R.string.emptyText),
            Snackbar.LENGTH_INDEFINITE
        ).also { snackbar ->
            snackbar.setAction(context.getString(android.R.string.ok)) {
                snackbar.dismiss()
            }
        }.show()
    } catch (e: Exception) {
        Log.e("snackbarLong", "${e.message}")
    }
}

fun View.loadingAnimation() {
    val fadeAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 0.15f)
    fadeAnim.duration = 500
    fadeAnim.repeatCount = ObjectAnimator.INFINITE
    fadeAnim.repeatMode = ObjectAnimator.REVERSE
    fadeAnim.start()
}