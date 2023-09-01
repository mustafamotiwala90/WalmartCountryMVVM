package com.example.walmartlabstest.data.api

import com.example.walmartlabstest.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit Response class which fetches a list of countries as a response.
 *
 * @property getAllCountries : This method fetches the list of countries.
 * */
interface CountryListApiService {
    @GET("countries.json")
    suspend fun getAllCountries() : Response<List<Country>>

}