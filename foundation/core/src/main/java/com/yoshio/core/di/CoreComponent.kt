package com.yoshio.core.di

import android.app.Application
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yoshio.core.coroutines.CoroutinesDispatchers
import com.yoshio.core.exceptions.ApiExceptionHandler
import com.yoshio.core.network.NetworkModule
import com.yoshio.core.preferences.di.PreferencesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PreferencesModule::class, NetworkModule::class])
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

    fun provideCoroutinesDispatchers(): CoroutinesDispatchers

    fun provideFirebaseAuth(): FirebaseAuth

    fun provideFirebaseFirestore(): FirebaseFirestore

    fun provideApiExceptionHandler(): ApiExceptionHandler

}
