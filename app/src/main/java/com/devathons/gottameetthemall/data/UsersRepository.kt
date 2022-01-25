package com.devathons.gottameetthemall.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UsersRepository(private val userDao: UserDao) {

    val users: List<User?> get() = getKnownUsers().completeWithNulls(TOTAL_OF_USERS)

    fun getKnownUsers():MutableList<User?> = runBlocking { userDao.getKnownUsers() }

    fun addNewUser(user: User) {
        GlobalScope.launch {
            userDao.insertUser(user)
        }
    }

    private fun <T> List<T>.completeWithNulls(totalOfElements: Int): List<T?> {
        val newList = this.toMutableList<T?>()
        val size = size
        if (size < totalOfElements) for (i in 1..totalOfElements - size) {
            newList.add(null)
        }
        return newList
    }

    companion object {
        private const val TOTAL_OF_USERS = 10
    }
}

