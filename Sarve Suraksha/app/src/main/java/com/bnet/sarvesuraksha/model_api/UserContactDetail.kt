package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserContactDetail {
    @SerializedName("_id")
    @Expose
    private var id: String? = null

    @SerializedName("personalId")
    @Expose
    private var personalId: String? = null

    @SerializedName("mobileNumber")
    @Expose
    private var mobileNumber: Long? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("createdAt")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    private var v: Int? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getPersonalId(): String? {
        return personalId
    }

    fun setPersonalId(personalId: String?) {
        this.personalId = personalId
    }

    fun getMobileNumber(): Long? {
        return mobileNumber
    }

    fun setMobileNumber(mobileNumber: Long?) {
        this.mobileNumber = mobileNumber
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getV(): Int? {
        return v
    }

    fun setV(v: Int?) {
        this.v = v
    }
}