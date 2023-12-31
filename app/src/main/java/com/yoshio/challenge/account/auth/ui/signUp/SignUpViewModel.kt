package com.yoshio.challenge.account.auth.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoshio.challenge.account.auth.domain.SignUpUseCase
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenLogin
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenSuccessSignUp
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenTakeId
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.RequestCameraPermission
import com.yoshio.core.coroutines.CoroutinesDispatchers
import com.yoshio.core.flow.Result
import com.yoshio.styling.livedata.Event
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase,
                                          private val validatePersonalDataExceptionHandler: ValidatePersonalDataExceptionHandler,
                                          private val coroutinesDispatchers: CoroutinesDispatchers) : ViewModel() {

    private val _signUpUiModelState = MutableLiveData<Event<SignUpUiModel>>()

    val signInUpModelState: LiveData<Event<SignUpUiModel>>
        get() = _signUpUiModelState

    private val mutableNavigateToSignUpAction = MutableLiveData<Event<SignUpActions>>()

    val navigateToSignUpAction: LiveData<Event<SignUpActions>>
        get() = mutableNavigateToSignUpAction

    fun validatePersonalData(userData: SignUpData) {
        val (areInvalidCredentials, exception) = validatePersonalDataExceptionHandler.areInvalidData(userData)
        if (areInvalidCredentials) return emitSignUpUiState(exception = exception)
        requiredId()
    }

    private fun requiredId() = emitSignUpUiState(isIdRequired = true)

    private fun registerError(exception: Exception) {
        exception.printStackTrace()
        emitSignUpUiState(exception = exception)
    }

    fun signUp(userData: SignUpData) {
        val (areInvalidCredentials, exception) = validatePersonalDataExceptionHandler.areInvalidData(userData)
        if (areInvalidCredentials) return emitSignUpUiState(exception = exception)
        emitSignUpUiState(showProgress = true)
        viewModelScope.launch(coroutinesDispatchers.io) {
            val result = signUpUseCase.signUp(userData)
            withContext(coroutinesDispatchers.main) {
                when (result) {
                    is Result.Success -> signUpSuccess()
                    is Result.Error -> registerError(result.exception)
                }
            }
        }
    }

    private fun signUpSuccess() = emitSignUpUiState(isRegisterSuccess = true)

    private fun emitSignUpUiState(showProgress: Boolean = false,
                                  isIdRequired: Boolean = false,
                                  isRegisterSuccess: Boolean = false,
                                  exception: Exception? = null) {
        _signUpUiModelState.value = Event(SignUpUiModel(
                showProgress = showProgress,
                isIdRequired = isIdRequired,
                isRegisterSuccess = isRegisterSuccess,
                exception = exception))
    }

    fun navigateToTakeIdAction(hasCameraPermission: Boolean) {
        mutableNavigateToSignUpAction.value = Event(if (hasCameraPermission) OpenTakeId else RequestCameraPermission)
    }

    fun navigateToRegisterSuccessAction() {
        mutableNavigateToSignUpAction.value = Event(OpenSuccessSignUp)
    }

    fun navigateToLoginAction() {
        mutableNavigateToSignUpAction.value = Event(OpenLogin)
    }
}
