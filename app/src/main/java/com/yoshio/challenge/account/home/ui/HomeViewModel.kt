package com.yoshio.challenge.account.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoshio.challenge.account.home.domain.GetUserDataUseCase
import com.yoshio.core.coroutines.CoroutinesDispatchers
import com.yoshio.core.flow.Result
import com.yoshio.styling.livedata.Event
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @Inject constructor(private val getUserDataUseCase: GetUserDataUseCase,
                                        private val coroutinesDispatchers: CoroutinesDispatchers) : ViewModel() {

    private val _userDataUiModelState = MutableLiveData<Event<UserDataUiModel>>()

    val userDataUiModel: LiveData<Event<UserDataUiModel>>
        get() = _userDataUiModelState

    private val mutableNavigateToUserDataActions = MutableLiveData<Event<UserDataActions>>()

    val navigateToUserDataAction: LiveData<Event<UserDataActions>>
        get() = mutableNavigateToUserDataActions


    fun getUserData(userId: String) {
        emitUserDataUiState(showProgress = true)
        viewModelScope.launch(coroutinesDispatchers.io) {
            val result = getUserDataUseCase.getUserInfo(userId)
            withContext(coroutinesDispatchers.main) {
                when (result) {
                    is Result.Success -> getUserDataSuccess(result.data.toUserInfoUi())
                    is Result.Error -> getUserDataError(result.exception)
                }
            }
        }
    }

    private fun getUserDataSuccess(userInfoUi: UserInfoUi) = emitUserDataUiState(userInfoUi = userInfoUi)

    private fun getUserDataError(exception: Exception) {
        exception.printStackTrace()
        emitUserDataUiState(exception = exception)
    }

    private fun emitUserDataUiState(showProgress: Boolean = false,
                                    userInfoUi: UserInfoUi? = null,
                                    exception: Exception? = null) {
        _userDataUiModelState.value = Event(UserDataUiModel(
                showProgress = showProgress,
                userInfoUi = userInfoUi,
                exception = exception))
    }
}
