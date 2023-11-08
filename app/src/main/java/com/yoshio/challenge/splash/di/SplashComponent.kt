package com.yoshio.challenge.splash.di

import com.yoshio.challenge.splash.SplashActivity
import com.yoshio.core.di.CoreComponent
import com.yoshio.core.di.scope.FeatureScope
import dagger.Component

@Component(dependencies = [CoreComponent::class])

@FeatureScope
interface SplashComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): SplashComponent
    }

    fun inject(splashActivity: SplashActivity)
}

