package com.example.walmartlabstest.view.state

import com.example.walmartlabstest.data.model.Country

data class CountriesListState( val isLoading: Boolean = false,
                               val countries: List<Country> = emptyList(),
                               val error: String = "")