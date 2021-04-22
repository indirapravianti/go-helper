package com.example.gotukang.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListItem(

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class UserList(

	@field:SerializedName("list")
	val list: List<ListItem?>? = null
) : Parcelable
