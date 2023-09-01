package com.example.walmartlabstest.domain

import com.example.walmartlabstest.data.model.Country
import com.example.walmartlabstest.network.NetworkRepository
import com.example.walmartlabstest.utils.ErrorConstants
import com.example.walmartlabstest.view.state.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * Simple Usecase class part of the domain layer hosting all the business logic of connecting the viewmodel with the UI.
 * This class is invoked from the viewmodel and uses the kotlin coroutine Flow to emit the responsive data (or error) back to the UI.
 * */
class GetCountriesListUseCase(
    private val networkRepository: NetworkRepository
) {

    operator fun invoke(): Flow<ViewState<List<Country>>> = flow {
        try {
            emit(ViewState.Loading())
            val usersResponse = networkRepository.countryApiService.getAllCountries()
            if(usersResponse.isSuccessful && usersResponse.body()!=null) {
                emit(ViewState.LoadSuccess(usersResponse.body()!!))
            }
        } catch(e: HttpException) {
            emit(ViewState.LoadFailure(e.localizedMessage ?: ErrorConstants.HTTP_ERROR))
        } catch(e: IOException) {
            emit(ViewState.LoadFailure(e.localizedMessage ?: ErrorConstants.INTERNET_ERROR))
        }
    }
}