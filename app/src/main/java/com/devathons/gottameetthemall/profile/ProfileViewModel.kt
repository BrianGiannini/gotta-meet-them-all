package com.devathons.gottameetthemall.profile

import androidx.lifecycle.ViewModel
import com.devathons.gottameetthemall.data.UserRepo
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val userRepo = UserRepo()

    fun display() = Timber.e("user ${getProfile()}")

    private fun getProfile() = userRepo.user

    fun saveProfile(firstName: String, lastName: String, job: String, description: String) {
        userRepo.updateUser(firstName, lastName, job, description)
    }
}