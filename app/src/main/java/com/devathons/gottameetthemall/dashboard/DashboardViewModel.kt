package com.devathons.gottameetthemall.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devathons.gottameetthemall.BaseViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import com.devathons.gottameetthemall.data.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class DashboardViewModel(
    private val profileRepository: ProfileRepository,
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    val isCurrentUserExists
        get() = runBlocking {
            Timber.e("here5 ${profileRepository.isCurrentUserExists()}")
            profileRepository.isCurrentUserExists()
        }

    val users: List<User?> get() = runBlocking { usersRepository.getAllUsers() }

    suspend fun getFlowUser(): Flow<User?> = profileRepository.getCurrentUser()

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