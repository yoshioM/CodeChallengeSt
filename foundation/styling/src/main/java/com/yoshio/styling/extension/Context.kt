package com.yoshio.styling.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> Context.intentTo(): Intent = Intent(this, T::class.java)

inline fun <reified T : AppCompatActivity> Context.clearIntentTo(): Intent = Intent(this, T::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
