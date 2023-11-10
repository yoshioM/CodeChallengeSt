package com.yoshio.styling.extension

import android.content.Intent

fun Intent?.getString(key: String) = this?.getStringExtra(key).orEmpty()

fun Intent?.getDouble(key: String) = this?.getDoubleExtra(key, Double.default()).orDefault()
