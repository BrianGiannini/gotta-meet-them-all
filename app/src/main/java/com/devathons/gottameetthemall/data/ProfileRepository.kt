package com.devathons.gottameetthemall.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Replace by class and use independence injection
class ProfileRepository(private val userDao: UserDao) {

    suspend fun getCurrentUser(): User? = userDao.getCurrentUser()

    //Fixme: We could use transaction to delete and insert a new object in the database.
    // it probably would require to distinct the updateProfile from a create profile
    // https://developer.android.com/reference/android/arch/persistence/room/Transaction
    suspend fun updateProfile(user: User) {
        val currentUser = userDao.getCurrentUser()
        currentUser?.let {
            userDao.deleteUser(it)
        }
        userDao.insertUser(user)
    }
}