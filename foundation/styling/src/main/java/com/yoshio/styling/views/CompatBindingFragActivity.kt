package com.yoshio.styling.views

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yoshio.styling.extension.inTransaction

abstract class CompatBindingFragActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction { add(containerId(), fragment()) }

    @IdRes
    protected abstract fun containerId(): Int

    protected abstract fun fragment(): Fragment
}
