package id.hudiohizari.githubuser.util

import android.content.Context
import android.util.Log
import id.hudiohizari.githubuser.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {

    private const val SERVER_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun getTimeAgo(context: Context, date: String?): String? {
        return date?.let {
            try {
                val format = SimpleDateFormat(SERVER_DATE_TIME, Locale("id", "ID"))
                val past = format.parse(date)
                if (past != null) {
                    val now = Date()
                    val seconds = TimeUnit.MILLISECONDS
                        .toSeconds(now.time - past.time).toInt()
                    val minutes = TimeUnit.MILLISECONDS
                        .toMinutes(now.time - past.time).toInt()
                    val hours = TimeUnit.MILLISECONDS
                        .toHours(now.time - past.time).toInt()
                    val days = TimeUnit.MILLISECONDS
                        .toDays(now.time - past.time).toInt()
                    when {
                        seconds < 60 -> context.resources.getQuantityString(
                            R.plurals.updatedSecondAgo, seconds
                        )
                        minutes < 60 -> context.resources.getQuantityString(
                            R.plurals.updatedMinuteAgo, minutes
                        )
                        hours < 24 -> context.resources.getQuantityString(
                            R.plurals.updatedHourAgo, hours
                        )
                        days < 7 -> context.resources.getQuantityString(
                            R.plurals.updatedDayAgo, days , days
                        )
                        else -> context.resources.getQuantityString(
                            R.plurals.updatedWeekAgo, days / 7, days / 7
                        )
                    }
                } else context.getString(R.string.wrongFormat)
            } catch (e: Exception) {
                Log.e("getTimeAgo", "${e.message}")
                context.getString(R.string.wrongFormat)
            }
        }
    }

}