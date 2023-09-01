package com.example.walmartlabstest.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("capital")
    val capital: String = "",
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("flag")
    val flag: String = "",
    @SerializedName("region")
    val region: String = ""
)