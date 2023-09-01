package com.example.walmartlabstest.utils

object AppConstants {

   const val COUNTRY_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/"

   // Cache values
   const val CACHE_CONTROL = "Cache-Control"
   const val CACHE_SIZE: Long = 5 * 1024 * 1024
   const val CACHE_VALUE_NETWORK = "public, max-age=" + 5
   const val CACHE_VALUE_NO_NETWORK = "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7

}