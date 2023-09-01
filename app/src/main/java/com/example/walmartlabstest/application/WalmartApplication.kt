package com.example.walmartlabstest.application

import android.app.Application
import android.content.Context

class WalmartApplication:Application() {

    // Instance of AppContainer that will be used by all the Activities of the app
    lateinit var appContainer: AppContainer

    init {
        instance = this
    }

    companion object {
        private var instance: WalmartApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = applicationContext()
        appContainer = AppContainer(context = context)
    }

}