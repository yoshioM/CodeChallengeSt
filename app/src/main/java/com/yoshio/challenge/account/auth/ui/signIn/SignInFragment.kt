package com.yoshio.challenge.account.auth.ui.signIn

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.di.initDagger
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.EmailEmpty
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.EmailInvalid
import com.yoshio.challenge.account.auth.ui.signIn.LoginException.PasswordEmpty
import com.yoshio.challenge.account.auth.ui.signIn.SignInActions.OpenHome
import com.yoshio.challenge.account.auth.ui.signIn.SignInActions.OpenSignUp
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActivity
import com.yoshio.challenge.account.home.ui.HomeActivity
import com.yoshio.challenge.databinding.FragmentSignInBinding
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.styling.extension.dismissKeyboard
import com.yoshio.styling.extension.getString
import com.yoshio.styling.extension.liveEventObserve
import com.yoshio.styling.extension.setOnSingleClickListener
import com.yoshio.styling.extension.showError
import com.yoshio.styling.extension.snackbar
import com.yoshio.styling.extension.viewBinding
import javax.inject.Inject
import com.yoshio.core.R as coreR

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding { FragmentSignInBinding.bind(requireView()) }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<SignInViewModel>

    private val signInViewModel by viewModels<SignInViewModel> { viewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        initObservers()
        initUi()
    }

    private fun initUi() {
        binding.signInButton.onClickListener = {
            cleanErrors()
            signInViewModel.login(email = binding.signInEditMail.getString(), password = binding.signInEditPassword.getString())
            binding.signInButton.dismissKeyboard()
        }

        binding.signUpButton.setOnSingleClickListener { signInViewModel.navigateToSignUpAction() }
    }

    private fun cleanErrors() {
        binding.signInInputLayoutMail.error = null
        binding.signInInputLayoutMail.error = null
    }

    private fun initObservers() {
        liveEventObserve(signInViewModel.signInUiModelState) { signInUi(it) }
        liveEventObserve(signInViewModel.navigateToSignInAction) { openSignInActions(it) }
    }

    private fun signInUi(signInUiModel: SignInUiModel) = signInUiModel.run {
        binding.signInButton.showProgress(showProgress)
        if (isLoginSuccess) signInUiSuccess(userId)
        if (exception != null) showErrorSnackBar(exception)
    }

    private fun signInUiSuccess(userId: String) = signInViewModel.navigateToHomeAction(userId)

    private fun showErrorSnackBar(exception: Exception) = exception.run {
        when (this) {
            is EmailEmpty -> binding.signInInputLayoutMail.error = getString(error)
            is EmailInvalid -> binding.signInInputLayoutMail.error = getString(error)
            is PasswordEmpty -> binding.signInInputLayoutPassword.error = getString(error)
            else -> snackbar(coreR.string.error_unknown).showError()
        }
    }

    private fun openSignInActions(signInActions: SignInActions) = signInActions.run {
        when (this) {
            is OpenHome -> launchHomeActivity(userId)
            is OpenSignUp -> launchSignUpActivity()
        }
    }

    private fun launchHomeActivity(userId: String) = startActivity(HomeActivity.createClearIntent(requireContext(), userId))

    private fun launchSignUpActivity() = startActivity(SignUpActivity.createIntent(requireContext()))


    override fun onDestroyView() {
        super.onDestroyView()
        binding.signInButton.clear()
    }

    companion object {

        fun newInstance() = SignInFragment()
    }
}
