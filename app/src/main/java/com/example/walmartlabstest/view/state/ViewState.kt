package com.example.walmartlabstest.view.state

/**
 * A simple state class to keep track of the different states of the UI. We use the standard T data model to ensure this can be reused for multiple fragments or views inside the class
 * We divide into 3 states :
 * - Loading state : for when internet connection is slow and data takes time to load we rely on this state.
 * - CountriesLoaded : This is the positive case for when the countries list is fully loaded.
 * - CountriesLoadFailiure : The edgecase for errors which we handle gracefully by not blocking the UI and instead showing an empty screen of content with an error message.
 * */
sealed class ViewState<T>(val data:T? = null, val message:String? = null) {
    class Loading<T>(data: T?=null): ViewState<T>(data)
    class LoadSuccess<T>(data: T): ViewState<T>(data)
    class LoadFailure<T>(message:String, data: T? =null): ViewState<T>(data, message)
}