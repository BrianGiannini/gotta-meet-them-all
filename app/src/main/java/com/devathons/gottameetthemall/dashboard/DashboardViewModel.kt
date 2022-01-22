package com.devathons.gottameetthemall.dashboard

import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository
import timber.log.Timber

class DashboardViewModel : ViewModel() {
    val users : List<User?> = UsersRepository.users
}