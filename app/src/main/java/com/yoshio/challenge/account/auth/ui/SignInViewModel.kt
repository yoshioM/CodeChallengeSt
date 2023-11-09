package com.yoshio.challenge.account.auth.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoshio.challenge.account.auth.domain.SingInUseCase
import com.yoshio.core.coroutines.CoroutinesDispatchers
import javax.inject.Inject
import kotlinx.coroutines.launch

class SignInViewModel @Inject constructor(private val singInUseCase: SingInUseCase,
                                          private val coroutinesDispatchers: CoroutinesDispatchers) : ViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val result = singInUseCase.login(email, password)

            Log.e("login: ", result.toString())

        }
    }
}
