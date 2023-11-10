package com.yoshio.styling.extension

import java.util.Locale

fun Double.Companion.default() = DEFAULT_VALUE

fun Double?.orDefault() = this ?: DEFAULT_VALUE

fun Double.toStringTwoDecimals(locale: Locale? = Locale.getDefault()) =
        String.format(locale, TWO_DECIMALS_FORMAT, this)

private const val DEFAULT_VALUE = 0.0
private const val TWO_DECIMALS_FORMAT = "%.2f"

