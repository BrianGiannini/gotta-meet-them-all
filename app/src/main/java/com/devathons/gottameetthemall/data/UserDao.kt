package com.devathons.gottameetthemall.data

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE isCurrent = 1")
    suspend fun getCurrentUser(): User?

    @Query("SELECT * FROM user WHERE firstName =:firstName AND lastName =:lastName")
    fun getUser(firstName: String, lastName: String?): User

    @Query("SELECT * FROM user where isCurrent = 0")
    suspend fun getKnownUsers(): MutableList<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}