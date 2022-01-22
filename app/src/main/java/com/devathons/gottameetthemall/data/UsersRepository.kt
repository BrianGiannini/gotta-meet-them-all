package com.devathons.gottameetthemall.data

// Replace object by class and use independence injection
object UsersRepository {
    private val knownUsers = mutableListOf<User?>()

    val users: List<User?> get() = knownUsers.completeWithNulls(TOTAL_OF_USERS)

    fun addNewUser(user: User) {
        if (!knownUsers.contains(user)) knownUsers.add(user)
    }

    private fun <T> List<T>.completeWithNulls(totalOfElements: Int): List<T?> {
        val newList = this.toMutableList<T?>()
        val size = size
        if (size < totalOfElements) for (i in 1..totalOfElements - size) {
            newList.add(null)
        }
        return newList
    }

    private const val TOTAL_OF_USERS = 10
}

