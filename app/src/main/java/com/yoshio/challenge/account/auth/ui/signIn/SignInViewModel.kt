package com.yoshio.challenge.account.auth.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoshio.challenge.account.auth.domain.SingInUseCase
import com.yoshio.challenge.account.auth.ui.signIn.SignInActions.OpenHome
import com.yoshio.challenge.account.auth.ui.signIn.SignInActions.OpenSignUp
import com.yoshio.challenge.account.home.domain.empty
import com.yoshio.core.coroutines.CoroutinesDispatchers
import com.yoshio.core.flow.Result
import com.yoshio.styling.livedata.Event
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel @Inject constructor(private val singInUseCase: SingInUseCase,
                                          private val loginExceptionHandler: LoginExceptionHandler,
                                          private val coroutinesDispatchers: CoroutinesDispatchers) : ViewModel() {

    private val _signInUiModelState = MutableLiveData<Event<SignInUiModel>>()

    val signInUiModelState: LiveData<Event<SignInUiModel>>
        get() = _signInUiModelState

    private val mutableNavigateToSignInAction = MutableLiveData<Event<SignInActions>>()

    val navigateToSignInAction: LiveData<Event<SignInActions>>
        get() = mutableNavigateToSignInAction

    fun login(email: String, password: String) {
        val (areInvalidCredentials, exception) = loginExceptionHandler.areInvalidCredentials(email, password)
        if (areInvalidCredentials) return emitSignInUiState(exception = exception)
        emitSignInUiState(showProgress = true)
        viewModelScope.launch(coroutinesDispatchers.io) {
            val result = singInUseCase.login(email, password)
            withContext(coroutinesDispatchers.main) {
                when (result) {
                    is Result.Success -> loginSuccess(result.data.userId)
                    is Result.Error -> loginError(result.exception)
                }
            }
        }
    }

    private fun loginSuccess(userId: String) = emitSignInUiState(isLoginSuccess = true, userId = userId)

    private fun loginError(exception: Exception) {
        exception.printStackTrace()
        emitSignInUiState(exception = exception)
    }

    private fun emitSignInUiState(showProgress: Boolean = false,
                                  isLoginSuccess: Boolean = false,
                                  userId: String = String.empty(),
                                  exception: Exception? = null) {
        _signInUiModelState.value = Event(SignInUiModel(
                showProgress = showProgress,
                isLoginSuccess = isLoginSuccess,
                userId = userId,
                exception = exception))
    }

    fun navigateToHomeAction(userId: String) {
        mutableNavigateToSignInAction.value = Event(OpenHome(userId))
    }

    fun navigateToSignUpAction() {
        mutableNavigateToSignInAction.value = Event(OpenSignUp)
    }
}
