package com.yoshio.challenge.account.home.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.yoshio.challenge.R
import com.yoshio.styling.extension.clearIntentTo
import com.yoshio.styling.views.CompatBindingFragActivity

class HomeActivity : CompatBindingFragActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
    }

    override fun containerId() = R.id.fragment_container

    override fun fragment() = HomeFragment.newInstance()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return false
    }

    companion object {

        fun createClearIntent(context: Context) = context.clearIntentTo<HomeActivity>()
    }
}
