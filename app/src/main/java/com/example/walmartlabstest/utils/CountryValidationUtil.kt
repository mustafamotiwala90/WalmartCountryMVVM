package com.example.walmartlabstest.utils

import com.example.walmartlabstest.data.model.Country

/**
 * Sanity check class to always make sure our JSON is formatted correctly and if not then do not add it to the list.
 * @property validateCountry : Simply checks if all the data fields are populated.
 * */
object CountryValidationUtil {

    fun validateCountry(country: Country) : Boolean {
        if (country.name.isNotEmpty() && country.region.isNotEmpty() && country.code.isNotEmpty() && country.capital.isNotEmpty()) {
            return true
        }
        return false
    }
}