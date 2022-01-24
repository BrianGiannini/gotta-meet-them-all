package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profile get() = profileRepository.user

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
}