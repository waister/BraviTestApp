package br.com.waister.bravitestapp.data.remote

import br.com.waister.bravitestapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteConfig<T>(
    private val service: Class<T>,
    private val baseUrl: String = BuildConfig.API_URL,
    private val endPoint: String,
) : KoinComponent {

    fun getApiService(): T {
        val httpClient = OkHttpClient.Builder()

        setTimeout(httpClient)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("$baseUrl$endPoint")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
            .create(service)
    }

    private fun setTimeout(httpClient: OkHttpClient.Builder, timeout: Long = 30) {
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
    }
}
