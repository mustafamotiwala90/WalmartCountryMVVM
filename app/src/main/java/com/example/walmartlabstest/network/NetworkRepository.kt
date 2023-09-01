package com.example.walmartlabstest.network

import com.example.walmartlabstest.data.api.CountryListApiService
import com.example.walmartlabstest.utils.AppConstants
import com.example.walmartlabstest.utils.AppConstants.CACHE_CONTROL
import com.example.walmartlabstest.utils.AppConstants.CACHE_VALUE_NETWORK
import com.example.walmartlabstest.utils.AppConstants.CACHE_VALUE_NO_NETWORK
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cache Network builder class sitting on top of retrofit to create a network request. This class does network request caching for easier access while offline as well as making it less intensive on the network when making repeated network calls.
 * It creates a cached OKHHTClient and uses the OKHHTClient cache to cache requests.
 *
 * @property hasNetwork : Checks if there is internet connection available
 * @property cache: Prebuilt OkHttp cache already supplied to the network builder.
 * */
open class NetworkRepository(private val hasNetwork: Boolean, private val cache: Cache?) {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.COUNTRY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createCacheOkHttpClient())
            .build()
    }

    private fun createCacheOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork)
                    request.newBuilder().header(CACHE_CONTROL, CACHE_VALUE_NETWORK).build()
                else
                    request.newBuilder().header(
                        CACHE_CONTROL,
                        CACHE_VALUE_NO_NETWORK
                    ).build()
                chain.proceed(request)
            }
            .build()
        return okHttpClient
    }

    var countryApiService: CountryListApiService =
        getRetrofit().create(CountryListApiService::class.java)
}