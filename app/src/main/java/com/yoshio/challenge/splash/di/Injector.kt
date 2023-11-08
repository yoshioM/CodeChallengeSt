package com.yoshio.challenge.splash.di

import com.yoshio.challenge.coreComponent
import com.yoshio.challenge.splash.SplashActivity

fun SplashActivity.initDagger() = DaggerSplashComponent.builder()
        .coreComponent(coreComponent())
        .build()
        .inject(this)

