package com.devathons.gottameetthemall

import android.app.Application
import com.devathons.gottameetthemall.data.AppDatabase
import com.devathons.gottameetthemall.data.ProfileRepository
import com.devathons.gottameetthemall.data.UsersRepository
import timber.log.Timber

class MyApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { AppDatabase.getDatabase(this) }
    val profileRepository by lazy { ProfileRepository(database.userDao()) }
    val usersRepository by lazy { UsersRepository(database.userDao()) }


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}