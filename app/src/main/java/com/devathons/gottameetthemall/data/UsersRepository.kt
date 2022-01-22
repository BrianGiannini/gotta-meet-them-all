package com.devathons.gottameetthemall.data

// Replace by class and use independence injection
object UsersRepository {
    val users
        get() = listOf(
            User("Timothée"),
            User("Brian"),
            User("Florian"),
        )
}