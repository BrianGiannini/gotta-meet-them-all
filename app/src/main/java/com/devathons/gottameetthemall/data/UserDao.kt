package com.devathons.gottameetthemall.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE isCurrent = 1")
    fun getCurrentUser(): User?

    @Query("SELECT * FROM user WHERE firstName =:firstName AND lastName =:lastName")
    fun getUser(firstName: String, lastName: String?): User

    @Query("SELECT * FROM user")
    fun getKnownUsers(): MutableList<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}