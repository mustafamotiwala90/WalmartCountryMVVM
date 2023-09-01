package com.example.walmartlabstest.application

import android.content.Context
import com.example.walmartlabstest.domain.GetCountriesListUseCase
import com.example.walmartlabstest.network.NetworkRepository
import com.example.walmartlabstest.utils.AppConstants
import com.example.walmartlabstest.utils.NetworkUtil
import okhttp3.Cache

class AppContainer(private val context:Context) {

    private fun createCache(): Cache {
        return Cache(context.cacheDir, AppConstants.CACHE_SIZE)
    }

    private val networkRepository = NetworkRepository(NetworkUtil.isNetworkAvailable(context = context), cache = createCache())
    val useCase = GetCountriesListUseCase(networkRepository = networkRepository)

}