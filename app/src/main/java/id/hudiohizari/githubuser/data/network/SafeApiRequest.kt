package id.hudiohizari.githubuser.data.network

import android.content.Context
import id.hudiohizari.githubuser.R
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest (
    private var context: Context
) {

    suspend fun <T: Any> apiRequest(
        call: suspend () -> Response<T>
    ): T? {
        val response = call.invoke()
        return checkForError(response)
    }

    private fun <T> checkForError(response: Response<T>): T? {
        if (response.isSuccessful) {
            return response.body()
        } else {
            @Suppress("BlockingMethodInNonBlockingContext")
            val error = response.errorBody()?.string()
            var code = response.code()
            var message = ""

            if (code >= 500) {
                message = context.getString(R.string.labelServerDown)
            } else {
                error?.let {
                    try {
                        code = JSONObject(it)
                        .getInt("code")
                        message = JSONObject(it)
                            .getString("message")
                    } catch (e: JSONException) {
                        try {
                            message = it
                        } catch (e: Exception) {
                            code = response.code()
                            message = "Error: ${response.message()}"
                        }
                    }
                }
            }

            throw ApiException(code, message)
        }
    }

}