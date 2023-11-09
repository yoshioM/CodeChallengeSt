package com.yoshio.challenge.account.auth.di

import com.yoshio.challenge.account.auth.ui.SignInFragment
import com.yoshio.core.di.CoreComponent
import com.yoshio.core.di.scope.FeatureScope
import dagger.Component

@Component(dependencies = [CoreComponent::class])

@FeatureScope
interface AccountComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): AccountComponent
    }

    fun inject(signInFragment: SignInFragment)
}
