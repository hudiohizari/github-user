package id.hudiohizari.githubuser.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import id.hudiohizari.githubuser.util.Constant
import id.hudiohizari.githubuser.util.extention.loadImageFromUrl

@BindingAdapter(value = ["imageUrl", "disableCenterCrop", "doNotLoadNull"], requireAll = false)
fun ImageView.imageUrl(
    url: String?,
    disableCenterCrop: Boolean = false,
    doNotLoadNull: Boolean = false
) {
    if (doNotLoadNull && url == null) {
        return
    }

    loadImageFromUrl(
        if (url.isNullOrEmpty()) Constant.IMAGE_INVALID
        else url,
        true,
        disableCenterCrop
    )
}