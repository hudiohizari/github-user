package id.hudiohizari.githubuser.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import id.hudiohizari.githubuser.R
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext
    private val context: Context
): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw ConnectionException(context.getString(R.string.cantConnectToInternet))
        try {
            return chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw ConnectionException(context.getString(R.string.failedToConnectToServer))
        } catch (e: Exception) {
            throw ConnectionException(context.getString(
                R.string.errorString, e.message ?: ""
            ))
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                @Suppress("DEPRECATION")
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }

}