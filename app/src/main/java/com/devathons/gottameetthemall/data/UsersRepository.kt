package com.devathons.gottameetthemall.data


class UsersRepository(private val userDao: UserDao) {

//    suspend fun getAllUsers(): List<User?> = getKnownUsers().first().completeWithNulls(TOTAL_OF_USERS)

    fun getKnownUsers() = userDao.getKnownUsers()

    suspend fun addNewUser(user: User) {
        userDao.insertUser(user)
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

