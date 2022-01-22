package com.devathons.gottameetthemall.data

// Replace by class and use independence injection
object UsersRepository {
    val users: List<User?>
        get() {
            return listOf(
                User("Timothée", "Rey-Vibet", "Product Owner", "Il est roux"),
                User("Brian", "Ginanini", "Cobotics Software Engineer", "Il est grand"),
                User("Florian", "Guyet", "HRI Software Engineer", "Il est barbu")
            ).completeWithNulls(TOTAL_OF_USERS)
        }

    private fun <T> List<T>.completeWithNulls(totalOfElements: Int): List<T?> {
        val newList = this.toMutableList<T?>()
        val size = size
        if (size < totalOfElements) for (i in 1..totalOfElements-size) {
            newList.add(null)
        }
        return newList
    }

    private const val TOTAL_OF_USERS = 10
}

