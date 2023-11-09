package com.yoshio.challenge.account.auth.ui.signIn

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.ui.signIn.AuthEntryPoint.SIGN_IN
import com.yoshio.styling.extension.clearIntentTo
import com.yoshio.styling.views.CompatBindingFragActivity

class AuthActivity : CompatBindingFragActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
    }

    override fun containerId() = R.id.fragment_container

    override fun fragment() = SignInFragment.newInstance()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return false
    }

    companion object {

        const val AUTH_ENTRY_POINT_EXTRA = "auth_entry_point"

        fun createClearIntent(context: Context, entryPoint: AuthEntryPoint = SIGN_IN) =
                context.clearIntentTo<AuthActivity>().putExtra(AUTH_ENTRY_POINT_EXTRA, entryPoint)
    }
}
