package id.hudiohizari.githubuser.util.extention

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import id.hudiohizari.githubuser.R

@SuppressLint("CheckResult")
fun ImageView.loadImageFromUrl(
    url: String,
    withHeader: Boolean = false,
    disableCenterCrop: Boolean = false
) {
    val option = RequestOptions()
        .timeout(15000)
        .placeholder(R.color.neutral_700)
        .error(R.drawable.image_invalid_url)

    if (!disableCenterCrop) option.centerCrop()

    val glideUrl = GlideUrl(
        url,
        LazyHeaders.Builder().addHeader(
            context.getString(R.string.tagUserAgent),
            context.getString(R.string.app_name)
        ).build()
    )

    try {
        Glide.with(context)
            .load(if (withHeader) glideUrl else url)
            .apply(option)
            .into(this)
    } catch (e: IllegalArgumentException) {
        Log.e("loadImageFromUrl", "${e.message}")
    }
}