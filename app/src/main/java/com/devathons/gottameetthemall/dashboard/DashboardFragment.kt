package com.devathons.gottameetthemall.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devathons.gottameetthemall.MyApplication
import com.devathons.gottameetthemall.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class DashboardFragment : Fragment(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    private val viewBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    private val viewModel: DashboardViewModel by lazy {
        val application: MyApplication = (activity?.application as MyApplication)
        val factory =
            DashboardViewModel.Factory(application.profileRepository, application.usersRepository)
        ViewModelProvider(this, factory)[DashboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = viewBinding.root

    override fun onResume() {
        super.onResume()
        Timber.e("here")
        val glide = Glide.with(this)
        //Timber.e("here2 ${viewModel.isCurrentUserExists}")
        /*if (!viewModel.isCurrentUserExists) {
            return
        }*/
        Timber.e("here3")
        launch {
            Timber.e("here4")
            viewModel.getFlowUser()
                .distinctUntilChanged()
                .collect {
                    Timber.e("here7 $it")
                    if (it == null){
                        createMyProfile()
                    }else {
                        Timber.e("here8 $it")
                        with(viewBinding) {
                            displayQrCodeButton.setOnClickListener {
                                QrCodeDialogFragment().show(
                                    parentFragmentManager,
                                    null
                                )
                            }
                            profileImage.setOnClickListener { displayMyProfile() }
                            profileName.text = "${it.firstName} ${it.lastName}"
                            profileJob.text = it.job
                        }
                        glide.load(it.picture).into(viewBinding.profileImage)
                    }
                }
        }

        val users = viewModel.users
        val discovered = users.count { it != null }
        val total = users.size
        viewBinding.score.text = "$discovered/$total users discovered:"

        val personsAdapter = UsersAdapter(users) { position ->
            val user = users.get(position) ?: return@UsersAdapter
            val action = DashboardFragmentDirections.actionShowUserFromDashboard(user)
            findNavController().navigate(action)
        }
        with(viewBinding.personsRecyclerView) {
            layoutManager = LinearLayoutManager(activity)
            adapter = personsAdapter
        }

        viewBinding.goScanButton.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToScanFragment()
            findNavController().navigate(action)
        }
    }

    private fun createMyProfile() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToProfileEditionFragment()
        findNavController().navigate(action)
    }

    private fun displayMyProfile() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun onPause() {
        job.cancel()
        super.onPause()
    }
}