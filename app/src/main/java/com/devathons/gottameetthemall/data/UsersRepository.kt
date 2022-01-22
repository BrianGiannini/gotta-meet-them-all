package com.devathons.gottameetthemall.data

// Replace by class and use independence injection
object UsersRepository {
    val users
        get() = listOf(
            User("Timothée", "Rey-Vibet", "Product Owner", "Il est roux"),
            User("Brian", "Ginanini", "Cobotics Software Engineer", "Il est grand"),
            User("Florian", "Guyet", "HRI Software Engineer", "Il est barbu")
        )
}