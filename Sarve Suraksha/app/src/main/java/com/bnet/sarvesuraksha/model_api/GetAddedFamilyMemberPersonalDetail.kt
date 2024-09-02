package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAddedFamilyMemberPersonalDetail {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("logInId")
    @Expose
    var logInId: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("DOB")
    @Expose
    var dob: String? = null

    @SerializedName("memberType")
    @Expose
    var memberType: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
