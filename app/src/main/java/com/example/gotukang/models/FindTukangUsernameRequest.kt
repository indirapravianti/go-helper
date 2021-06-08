package com.example.gotukang.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FindTukangUsernameRequest(
    @field:SerializedName("username")
    val username: String? = null
) : Parcelable
