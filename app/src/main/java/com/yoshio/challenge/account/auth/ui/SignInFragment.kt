package com.yoshio.challenge.account.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yoshio.challenge.R
import com.yoshio.challenge.account.auth.di.initDagger
import com.yoshio.challenge.databinding.FragmentSignInBinding
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.styling.extension.viewBinding
import javax.inject.Inject

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding { FragmentSignInBinding.bind(requireView()) }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<SignInViewModel>

//    private val signInViewModel by viewModels<SignInViewModel> { viewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.signInButton.clear()
    }

    companion object {

        fun newInstance() = SignInFragment()
    }
}
