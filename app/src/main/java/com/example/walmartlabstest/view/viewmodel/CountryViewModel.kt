package com.example.walmartlabstest.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmartlabstest.domain.GetCountriesListUseCase
import com.example.walmartlabstest.utils.ErrorConstants
import com.example.walmartlabstest.view.state.CountriesListState
import com.example.walmartlabstest.view.state.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * The core part of the app. As part of the MVVM architecture, this is the view model class which interacts between the View layer and the model layer.
 * This class extends the standard AndroidX ViewModel class and hooks up with the domain layer to fetch the result of the countries. It separates the result depending on the state to set the correct livedata value.
 *
 * */
class CountryViewModel(
    val countryUseCase: GetCountriesListUseCase
) : ViewModel() {

    private val _countryData = MutableLiveData<CountriesListState>()
    val countryData: LiveData<CountriesListState> = _countryData


    fun fetchCountries() {
        countryUseCase().onEach { result ->
            when (result) {
                is ViewState.LoadSuccess -> {
                    _countryData.value = CountriesListState(countries = result.data?: emptyList())
                }
                is ViewState.LoadFailure -> {
                    _countryData.value = CountriesListState(error = result.message?: ErrorConstants.HTTP_ERROR)
                }
                is ViewState.Loading -> {
                    _countryData.value = CountriesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        _countryData.value = CountriesListState(isLoading = true, countries = emptyList(), error = "ViewModel was cleared")
    }
}
