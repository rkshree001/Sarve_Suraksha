package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class PostUserBasicDetailPersonalMainRes {

    @SerializedName("logInId")
    @Expose
    private var logInId: String? = null

    @SerializedName("fullName")
    @Expose
    private var fullName: String? = null

    @SerializedName("gender")
    @Expose
    private var gender: String? = null

    @SerializedName("DOB")
    @Expose
    private var dob: String? = null

    @SerializedName("memberType")
    @Expose
    private var memberType: String? = null

    @SerializedName("_id")
    @Expose
    private var id: String? = null

    @SerializedName("createdAt")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    private var v: Int? = null

    fun getLogInId(): String? {
        return logInId
    }

    fun setLogInId(logInId: String?) {
        this.logInId = logInId
    }

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String?) {
        this.fullName = fullName
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }

    fun getDob(): String? {
        return dob
    }

    fun setDob(dob: String?) {
        this.dob = dob
    }

    fun getMemberType(): String? {
        return memberType
    }

    fun setMemberType(memberType: String?) {
        this.memberType = memberType
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
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