package com.yoshio.challenge.account.home.di

import com.yoshio.challenge.account.home.ui.HomeFragment
import com.yoshio.core.di.CoreComponent
import com.yoshio.core.di.scope.FeatureScope
import dagger.Component

@Component(dependencies = [CoreComponent::class])

@FeatureScope
interface HomeComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): HomeComponent
    }

    fun inject(homeFragment: HomeFragment)

}
