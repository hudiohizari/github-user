package id.hudiohizari.githubuser.data.network

import id.hudiohizari.githubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor@Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        request = request.newBuilder()
            .addHeader("Authorization", "token ${BuildConfig.TOKEN}")
            .build()

        return chain.proceed(request)
    }

}