package com.example.exchangerate.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SearchCurrency(
    @PrimaryKey(autoGenerate = false)
    val code: String,

    val lastUpdateUnixTimestamp: Long
) : Parcelable