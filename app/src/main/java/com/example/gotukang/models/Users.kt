package com.example.gotukang.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(

	@field:SerializedName("Users")
	val users: Users? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
