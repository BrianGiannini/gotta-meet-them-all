package com.devathons.gottameetthemall.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["firstName", "lastName"])
@Parcelize
data class User(
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "job") val job: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "isCurrent") val isCurrent: Boolean = false,
) : Parcelable {
    fun generateQRCode(): User {
        return User(firstName, lastName, job, description, false)
    }
}

