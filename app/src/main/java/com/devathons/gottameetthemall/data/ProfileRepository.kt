package com.devathons.gottameetthemall.data

import timber.log.Timber

// Replace by class and use independence injection
object ProfileRepository {
    var user: User = User("Timothée", "Rey Vibet", "Product Owner")
        private set

    fun updateUser(firstName: String, lastName: String, job: String, description: String) {
        Timber.e("$firstName $lastName $job $description")
        user = User(firstName, lastName, job, description)
        Timber.e("user $user")
    }
}