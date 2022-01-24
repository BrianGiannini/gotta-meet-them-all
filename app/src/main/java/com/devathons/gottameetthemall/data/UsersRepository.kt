package com.devathons.gottameetthemall.data

class UsersRepository(private val userDao: UserDao) {

    private val knownUsers:MutableList<User?> = userDao.getKnownUsers()

    val users: List<User?> get() = knownUsers.completeWithNulls(TOTAL_OF_USERS)

    fun addNewUser(user: User) {
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

