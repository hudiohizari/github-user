package id.hudiohizari.githubuser.ui.user.detail

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import id.hudiohizari.githubuser.R
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse

@BindingAdapter("getCommunityText")
fun TextView.getCommunityText(detailResponse: DetailResponse?) {
    detailResponse?.let {
        val followers = detailResponse.getFormattedFollowers()
        val spannableFollowers = SpannableString(followers)
        spannableFollowers.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            spannableFollowers.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        append(spannableFollowers)
        append(context.getString(R.string.followers))
        val spannableFollowing = SpannableString(detailResponse.getFormattedFollowing())
        spannableFollowing.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            spannableFollowing.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        append(spannableFollowing)
        append(context.getString(R.string.following))
    }
}