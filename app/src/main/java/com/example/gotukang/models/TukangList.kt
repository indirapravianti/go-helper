package com.example.gotukang.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TukangListItem(

	@field:SerializedName("review")
	val review: String? = null,

	@field:SerializedName("specialization")
	val specialization: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("price")
	val price: String? = null
) : Parcelable

@Parcelize
data class TukangList(

	@field:SerializedName("list")
	val tukangList: List<TukangListItem?>? = null
) : Parcelable
