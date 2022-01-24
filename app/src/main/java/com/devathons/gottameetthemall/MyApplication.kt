package com.devathons.gottameetthemall

import android.app.Application
import com.devathons.gottameetthemall.data.AppDatabase
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.UsersRepository
import timber.log.Timber

class MyApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: AppDatabase? = null
    val usersRepository: UsersRepository? = null
    val profileRepository: ProfileRepository? = null


    override fun onCreate() {
        super.onCreate()

        val database = AppDatabase.getDatabase(this)
        val usersRepository = UsersRepository(database.userDao())
        val profileRepository = ProfileRepository(database.userDao())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}