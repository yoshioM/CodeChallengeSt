package com.yoshio.challenge.account.auth.di

import androidx.fragment.app.Fragment
import com.yoshio.challenge.account.auth.ui.signIn.SignInFragment
import com.yoshio.challenge.account.auth.ui.signUp.SignUpFragment
import com.yoshio.challenge.coreComponent

fun SignInFragment.initDagger() = daggerAccountComponent().inject(this)

fun SignUpFragment.initDagger() = daggerAccountComponent().inject(this)

private fun Fragment.daggerAccountComponent() = DaggerAccountComponent.builder()
        .coreComponent(requireActivity().coreComponent())
        .build()

