package com.yoshio.core.di

import android.app.Application
import android.content.SharedPreferences
import com.yoshio.core.preferences.di.PreferencesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PreferencesModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }

    fun provideApplication(): Application

    fun provideSharedPreferences(): SharedPreferences
}
