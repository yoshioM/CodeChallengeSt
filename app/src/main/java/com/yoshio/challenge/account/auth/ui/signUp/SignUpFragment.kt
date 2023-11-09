package com.yoshio.challenge.account.auth.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.di.initDagger
import com.yoshio.challenge.databinding.FragmentSignUpBinding
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.styling.extension.setOnToolbarBackPressed
import com.yoshio.styling.extension.viewBinding
import javax.inject.Inject

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding { FragmentSignUpBinding.bind(requireView()) }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<SignUpViewModel>

    private val signUpViewModel by viewModels<SignUpViewModel> { viewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        initSupportActionBar()
        initUi()
    }

    private fun initSupportActionBar() {
        setOnToolbarBackPressed(binding.signUpToolbar)
    }

    private fun initUi() {
        binding.signUpButton.onClickListener = {}
    }

    companion object {

        fun newInstance() = SignUpFragment()
    }
}
