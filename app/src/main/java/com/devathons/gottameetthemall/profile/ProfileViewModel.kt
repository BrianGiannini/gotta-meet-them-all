package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.User

class ProfileViewModel : ViewModel() {

    val profile get() = ProfileRepository.user

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        ProfileRepository.user = User(firstName, lastName, job, description)
    }
}