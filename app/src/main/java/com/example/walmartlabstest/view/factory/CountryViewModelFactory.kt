package com.example.walmartlabstest.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walmartlabstest.domain.GetCountriesListUseCase
import com.example.walmartlabstest.view.viewmodel.CountryViewModel

/**
 * Simple ViewModelFactory class for our countries API. In the future this could be a more generic class invoking several other instance factories.
 * */
@Suppress("UNCHECKED_CAST")
class CountryViewModelFactory(private val useCase: GetCountriesListUseCase) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            CountryViewModel(this.useCase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}