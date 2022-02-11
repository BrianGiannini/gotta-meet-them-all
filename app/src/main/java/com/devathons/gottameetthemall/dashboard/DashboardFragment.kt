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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

        launch {
            viewModel.currentUser.collect {currentUser ->
                if (currentUser == null) {
                    createMyProfile()
                } else {
                    with(viewBinding) {
                        displayQrCodeButton.setOnClickListener { QrCodeDialogFragment().show(parentFragmentManager, null) }
                        profileImage.setOnClickListener { displayMyProfile() }
                        profileName.text = "${currentUser.firstName} ${currentUser.lastName}"
                        profileJob.text = currentUser.job
                    }
                    Glide.with(this@DashboardFragment).load(currentUser.picture).into(viewBinding.profileImage)

                    viewModel.users.collect { users ->
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
                    }
                }
            }
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
}