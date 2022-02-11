package com.devathons.gottameetthemall.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE isCurrent = 1")
    fun getCurrentUser(): Flow<User?>

    @Query("SELECT * FROM user WHERE firstName =:firstName AND lastName =:lastName")
    fun getUser(firstName: String, lastName: String?): Flow<User>

    @Query("SELECT * FROM user where isCurrent = 0")
    fun getKnownUsers(): Flow<MutableList<User?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}