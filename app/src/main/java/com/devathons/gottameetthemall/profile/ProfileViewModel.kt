package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profile get() = profileRepository.getCurrentUser()

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        profileRepository.updateProfile(
            User(
                firstName = firstName,
                lastName = lastName,
                job = job,
                description = description,
                isCurrent = true
            )
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(profileRepository) as T
        }
    }
}