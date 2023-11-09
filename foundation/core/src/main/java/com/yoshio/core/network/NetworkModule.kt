package com.yoshio.core.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yoshio.core.exceptions.ApiExceptionHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideFirebase() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideApiExceptionHandler() = ApiExceptionHandler()
}
