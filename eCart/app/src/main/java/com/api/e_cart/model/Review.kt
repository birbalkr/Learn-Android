package com.api.e_cart.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Review(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
): Parcelable