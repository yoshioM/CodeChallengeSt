package com.yoshio.styling.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().apply { commit() }

fun <T> Fragment.viewBinding(initialise: () -> T): ReadOnlyProperty<Fragment, T> =
        object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

            private var binding: T? = null
            private var viewLifecycleOwner: LifecycleOwner? = null

            init {

                liveDataObserve(this@viewBinding.viewLifecycleOwnerLiveData) { newLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.removeObserver(this)
                    viewLifecycleOwner = newLifecycleOwner.also { it?.lifecycle?.addObserver(this) }
                }
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                binding = null
            }

            override fun getValue(thisRef: Fragment, property: KProperty<*>): T = this.binding ?: initialise().also { this.binding = it }
        }
