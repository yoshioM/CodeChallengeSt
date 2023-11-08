package com.yoshio.challenge

import android.app.Activity
import android.app.Application
import android.content.Context
import com.yoshio.core.di.CoreComponent
import com.yoshio.core.di.DaggerCoreComponent

class App : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder().application(this).build()
    }

    companion object {

        @JvmStatic
        fun coreComponent(context: Context) = (context.applicationContext as App).coreComponent

    }
}

fun Activity.coreComponent() = App.coreComponent(this)

