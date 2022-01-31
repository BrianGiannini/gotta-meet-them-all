package com.devathons.gottameetthemall.data

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

// Replace by class and use independence injection
class ProfileRepository(private val userDao: UserDao) {

    suspend fun isCurrentUserExists(): Boolean = userDao.isCurrentUserExist()

    suspend fun getCurrentUser(): Flow<User?> = flow { emit(userDao.getCurrentUser()) }




    //Fixme: We could use transaction to delete and insert a new object in the database.
    // it probably would require to distinct the updateProfile from a create profile
    // https://developer.android.com/reference/android/arch/persistence/room/Transaction
    suspend fun updateProfile(user: User) {
        val currentUser = userDao.getCurrentUser()
        currentUser.let {
            userDao.deleteUser(it)
        }
        userDao.insertUser(user)
    }
}