package com.example.gotukang.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddOrderRequest(
	val usernameTk: String? = null,
	val location: String? = null,
	val usernameUs: String? = null
) : Parcelable
