package com.yoshio.core.preferences.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object PreferencesModule {

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Named("configurationPreferences")
    fun provideConfigurationPreferences(application: Application): SharedPreferences {
        return EncryptedSharedPreferences.create(
                CONFIGURATION_PREFERENCES,
                MasterKeys.getOrCreate(AES256_GCM_SPEC),
                application,
                PrefKeyEncryptionScheme.AES256_SIV,
                PrefValueEncryptionScheme.AES256_GCM)
    }

    private const val CONFIGURATION_PREFERENCES = "configuration_preferences"
}
