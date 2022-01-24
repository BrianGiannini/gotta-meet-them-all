package com.devathons.gottameetthemall.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository

class DashboardViewModel(
    private val usersRepository: UsersRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val currentUser get() = profileRepository.user
    val users: List<User?> get() = usersRepository.users

    @Suppress("UNCHECKED_CAST")
    class Factory(private val usersRepository: UsersRepository,
                  private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DashboardViewModel(usersRepository, profileRepository) as T
        }
    }
}