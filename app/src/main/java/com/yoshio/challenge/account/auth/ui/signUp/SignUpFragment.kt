package com.yoshio.challenge.account.auth.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.di.initDagger
import com.yoshio.challenge.account.auth.ui.signIn.AuthActivity
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenLogin
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenSuccessSignUp
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.OpenTakeId
import com.yoshio.challenge.account.auth.ui.signUp.SignUpActions.RequestCameraPermission
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.ConfirmPasswordEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.EmailEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.EmailInvalid
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.LastNameEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.NameEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.PasswordDontMatchEmpty
import com.yoshio.challenge.account.auth.ui.signUp.UserDataException.PasswordEmpty
import com.yoshio.challenge.databinding.FragmentSignUpBinding
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.styling.extension.AlertDialogButton
import com.yoshio.styling.extension.getString
import com.yoshio.styling.extension.getUriForImage
import com.yoshio.styling.extension.hasCameraPermission
import com.yoshio.styling.extension.liveEventObserve
import com.yoshio.styling.extension.requestCameraPermission
import com.yoshio.styling.extension.setOnToolbarBackPressed
import com.yoshio.styling.extension.showAlertDialog
import com.yoshio.styling.extension.showError
import com.yoshio.styling.extension.showPermissionDialog
import com.yoshio.styling.extension.snackbar
import com.yoshio.styling.extension.viewBinding
import javax.inject.Inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding { FragmentSignUpBinding.bind(requireView()) }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<SignUpViewModel>

    private val signUpViewModel by viewModels<SignUpViewModel> { viewModelProviderFactory }

    private val requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
        signUpViewModel.navigateToTakeIdAction(hasCameraPermission = permissionGranted)
    }

    private val takeAPhoto = registerForActivityResult(TakePicture()) { hasSaved ->
        if (hasSaved) signUpViewModel.signUp(getUserData())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        initSupportActionBar()
        initObservers()
        initUi()
    }

    private fun initSupportActionBar() {
        setOnToolbarBackPressed(binding.signUpToolbar)
    }

    private fun initObservers() {
        liveEventObserve(signUpViewModel.signInUpModelState) { signUpUi(it) }
        liveEventObserve(signUpViewModel.navigateToSignUpAction) { openSignUpActions(it) }
    }

    private fun initUi() {
        binding.signUpButton.onClickListener = {
            cleanErrors()
            signUpViewModel.validatePersonalData(getUserData())
        }
    }

    private fun signUpUi(signUpUiModel: SignUpUiModel) = signUpUiModel.run {
        binding.signUpButton.showProgress(showProgress)
        if (isIdRequired) openTakeIdPhoto()
        if (isRegisterSuccess) signUpUiSuccess()
        if (exception != null) showErrorSnackBar(exception)
    }

    private fun openTakeIdPhoto() = requireContext().showAlertDialog(
            title = getString(R.string.id_required_title),
            message = getString(R.string.id_security),
            cancelable = false,
            positiveButton = AlertDialogButton(
                    action = { signUpViewModel.navigateToTakeIdAction(requireActivity().hasCameraPermission()) }),
            negativeButton = AlertDialogButton(textButton = getString(R.string.cancel))
    )

    private fun signUpUiSuccess() = signUpViewModel.navigateToRegisterSuccessAction()

    private fun showErrorSnackBar(exception: Exception) = exception.run {
        when (this) {
            is NameEmpty -> binding.nameInputLayout.error = getString(error)
            is LastNameEmpty -> binding.lastNameInputLayout.error = getString(error)
            is EmailEmpty -> binding.emailInputLayout.error = getString(error)
            is EmailInvalid -> binding.emailInputLayout.error = getString(error)
            is PasswordEmpty -> binding.passwordInputLayout.error = getString(error)
            is ConfirmPasswordEmpty -> binding.confirmPasswordLayout.error = getString(error)
            is PasswordDontMatchEmpty -> binding.confirmPasswordLayout.error = getString(error)
            else -> snackbar(com.yoshio.core.R.string.error_unknown).showError()
        }
    }

    private fun cleanErrors() {
        binding.nameInputLayout.error = null
        binding.lastNameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordLayout.error = null
        binding.confirmPasswordLayout.error = null
    }

    private fun openSignUpActions(signUpActions: SignUpActions) {
        when (signUpActions) {
            OpenSuccessSignUp -> showSuccessDialog()
            OpenTakeId -> openCamera()
            RequestCameraPermission -> showPermissionDialog(
                    title = getString(R.string.permission_required),
                    message = getString(R.string.permission_required_message),
                    onAccept = { requestCamera.requestCameraPermission() })

            OpenLogin -> launchSignInActivity()
        }
    }

    private fun openCamera() {
        val uri = requireContext().getUriForImage(getString(R.string.authority), APP_DIRECTORY)
        takeAPhoto.launch(uri)
    }

    private fun showSuccessDialog() = requireContext().showAlertDialog(
            title = getString(R.string.register_success),
            message = getString(R.string.complete_login),
            cancelable = false,
            positiveButton = AlertDialogButton(action = { signUpViewModel.navigateToLoginAction() })
    )

    private fun launchSignInActivity() = startActivity(AuthActivity.createClearIntent(requireContext()))


    private fun getUserData() = SignUpData(
            binding.nameEdit.getString(),
            binding.lastNameEdit.getString(),
            binding.emailEdit.getString(),
            binding.passwordEdit.getString(),
            binding.confirmPasswordEdit.getString())

    companion object {
        const val APP_DIRECTORY = "/challenge_pictures/"

        fun newInstance() = SignUpFragment()
    }
}
