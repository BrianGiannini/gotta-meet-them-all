package com.devathons.gottameetthemall.data

import timber.log.Timber

class UserRepo {
    var user: User = User("Tim")

    fun updateUser(firstName: String, lastName: String, job: String, description: String) {
        Timber.e("$firstName $lastName $job $description")
        user = User(firstName, lastName, job, description)
        Timber.e("user $user")
    }
}