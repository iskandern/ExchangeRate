package com.example.exchangerate.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(foreignKeys = [
    ForeignKey(
        entity = SearchCurrency::class,
        parentColumns = ["code"],
        childColumns = ["baseCurrencyCode"]
    )
], primaryKeys = [ "code", "baseCurrencyCode" ]
)
data class ConversionCurrency(
    val code: String,

    val rate: Double,

    val baseCurrencyCode: String
) : Parcelable