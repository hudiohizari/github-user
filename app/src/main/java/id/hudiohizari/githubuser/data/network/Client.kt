package id.hudiohizari.githubuser.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client @Inject constructor(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val chuckerInterceptor: ChuckerInterceptor
) {

    fun provideClient(): OkHttpClient {
        val interceptorLogging = HttpLoggingInterceptor()
        interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptorLogging)
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}