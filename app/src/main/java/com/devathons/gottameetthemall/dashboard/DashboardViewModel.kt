package com.devathons.gottameetthemall.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository

class DashboardViewModel(
    private val profileRepository: ProfileRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {
    val currentUser get() = profileRepository.getCurrentUser()
    val users: List<User?> get() = usersRepository.users

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val profileRepository: ProfileRepository,
        private val usersRepository: UsersRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(profileRepository, usersRepository) as T
        }
    }
}