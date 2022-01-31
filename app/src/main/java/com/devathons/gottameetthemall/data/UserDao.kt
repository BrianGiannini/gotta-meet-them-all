package com.devathons.gottameetthemall.data

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT EXISTS(SELECT * FROM user WHERE isCurrent = 1)")
    suspend fun isCurrentUserExist(): Boolean

    @Query("SELECT * FROM user WHERE isCurrent = 1")
    suspend fun getCurrentUser(): User

    @Query("SELECT * FROM user WHERE firstName =:firstName AND lastName =:lastName")
    suspend fun getUser(firstName: String, lastName: String?): User

    @Query("SELECT * FROM user where isCurrent = 0")
    suspend fun getKnownUsers(): MutableList<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}