package com.devathons.gottameetthemall.profileedition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devathons.gottameetthemall.BaseViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileEditionViewModel(private val profileRepository: ProfileRepository) : BaseViewModel() {

    //fun getCurrentUser(): User? = profileRepository.getCurrentUser()

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        launch {
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
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileEditionViewModel(profileRepository) as T
        }
    }
}