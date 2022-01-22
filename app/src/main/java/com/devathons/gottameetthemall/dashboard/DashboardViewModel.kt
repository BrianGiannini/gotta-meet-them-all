package com.devathons.gottameetthemall.dashboard

import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository

class DashboardViewModel : ViewModel() {
    val currentUser get() = ProfileRepository.user
    val users: List<User?> get() = UsersRepository.users
}