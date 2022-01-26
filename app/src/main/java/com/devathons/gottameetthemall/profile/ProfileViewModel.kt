package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.BaseViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val profileRepository: ProfileRepository) : BaseViewModel() {

    fun getCurrentUser(): User? = runBlocking { profileRepository.getCurrentUser() }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(profileRepository) as T
        }
    }
}