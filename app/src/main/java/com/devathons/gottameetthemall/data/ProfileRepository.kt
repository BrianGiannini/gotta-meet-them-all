package com.devathons.gottameetthemall.data

// Replace by class and use independence injection
class ProfileRepository(private val userDao: UserDao) {

    var user: User? = userDao.getCurrentUser()

    fun updateProfile(user: User) {
        userDao.insertUser(user)
    }
}