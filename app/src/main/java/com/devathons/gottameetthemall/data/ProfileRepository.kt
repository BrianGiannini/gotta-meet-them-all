package com.devathons.gottameetthemall.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Replace by class and use independence injection
class ProfileRepository(private val userDao: UserDao) {

    fun getCurrentUser() = runBlocking { userDao.getCurrentUser() }

    fun updateProfile(user: User) {
        GlobalScope.launch {
            val currentUser = userDao.getCurrentUser()
            currentUser?.let {
                userDao.deleteUser(it)
            }
            userDao.insertUser(user)
        }
    }
}