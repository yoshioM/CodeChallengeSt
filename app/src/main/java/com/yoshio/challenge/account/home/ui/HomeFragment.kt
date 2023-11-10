package com.yoshio.challenge.account.home.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yoshio.challenge.R
import com.yoshio.challenge.account.home.di.initDagger
import com.yoshio.challenge.account.home.ui.HomeActivity.Companion.USER_ID_EXTRA
import com.yoshio.challenge.account.home.ui.UserDataActions.OpenDetailTransaction
import com.yoshio.challenge.account.home.ui.transaction.TransactionDetailActivity
import com.yoshio.challenge.databinding.FragmentHomeBinding
import com.yoshio.core.di.viewmodel.ViewModelProviderFactory
import com.yoshio.core.exceptions.ApiRequestException
import com.yoshio.styling.extension.getActivityIntent
import com.yoshio.styling.extension.hideOrShow
import com.yoshio.styling.extension.liveEventObserve
import com.yoshio.styling.extension.showError
import com.yoshio.styling.extension.snackbar
import com.yoshio.styling.extension.viewBinding
import com.yoshio.styling.extension.visible
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding { FragmentHomeBinding.bind(requireView()) }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory<HomeViewModel>

    private val homeViewModel by viewModels<HomeViewModel> { viewModelProviderFactory }

    private val transactionAdapter by lazy { TransactionAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDagger()
        getUserData()
        initObservers()
        initRecyclerView()
        initAdapter()
    }

    private fun initObservers() {
        liveEventObserve(homeViewModel.userDataUiModel) { userDataUi(it) }
        liveEventObserve(homeViewModel.navigateToUserDataAction) { userDataActions(it) }
    }

    private fun initRecyclerView() = binding.transactionRecyclerView.run {
        adapter = transactionAdapter
    }

    private fun initAdapter() = transactionAdapter.run {
        onTransactionListener = { homeViewModel.navigateToDetail(it) }
    }

    private fun userDataUi(userDataUiModel: UserDataUiModel) = userDataUiModel.run {
        binding.homeProgressBar.hideOrShow(showProgress)
        if (userInfoUiModel != null) userDataUiSuccess(userInfoUiModel)
        if (exception != null) showErrorSnackBar(exception)
    }

    private fun userDataUiSuccess(userInfoUiModel: UserInfoUiModel) = userInfoUiModel.run {
        binding.titleTextView.text = getString(R.string.home_title, name, lastName)
        binding.titleTextView.visible()
        binding.balanceTextView.text = getString(R.string.total_balance, balanceAmount)
        binding.balanceTextView.visible()
        binding.transactionRecyclerView.run {
            transactionAdapter.set(transactionList)
        }
    }

    private fun showErrorSnackBar(exception: Exception) = exception.run {
        when (this) {
            is ApiRequestException -> snackbar(messageError).showError()
            else -> snackbar(com.yoshio.core.R.string.error_unknown).showError()
        }
    }

    private fun userDataActions(userDataActions: UserDataActions) = userDataActions.run {
        when (this) {
            is OpenDetailTransaction -> navigateToTransactionDetail(transactionUiModel)
        }
    }

    private fun navigateToTransactionDetail(transactionUiModel: TransactionUiModel) =
            startActivity(TransactionDetailActivity.createIntent(requireContext(), transactionUiModel))

    private fun getUserData() = homeViewModel.getUserData(getUserId())

    private fun getUserId() = getActivityIntent()?.getStringExtra(USER_ID_EXTRA).orEmpty()

    companion object {

        fun newInstance() = HomeFragment()
    }
}
