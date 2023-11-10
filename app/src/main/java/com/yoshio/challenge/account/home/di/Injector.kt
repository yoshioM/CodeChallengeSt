package com.yoshio.challenge.account.home.di

import androidx.fragment.app.Fragment
import com.yoshio.challenge.account.home.ui.HomeFragment
import com.yoshio.challenge.coreComponent

fun HomeFragment.initDagger() = daggerHomeComponent().inject(this)

private fun Fragment.daggerHomeComponent() = DaggerHomeComponent.builder()
        .coreComponent(requireActivity().coreComponent())
        .build()
