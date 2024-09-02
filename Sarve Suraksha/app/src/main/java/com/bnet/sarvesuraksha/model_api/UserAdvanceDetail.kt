package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserAdvanceDetail {
    @SerializedName("_id")
    @Expose
    private var id: String? = null

    @SerializedName("personalId")
    @Expose
    private var personalId: String? = null

    @SerializedName("maritalStatus")
    @Expose
    private var maritalStatus: String? = null

    @SerializedName("createdAt")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    private var v: Int? = null

    @SerializedName("occupation")
    @Expose
    private var occupation: UserOccupation? = null

    @SerializedName("qualification")
    @Expose
    private var qualification: String? = null

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

    fun getMaritalStatus(): String? {
        return maritalStatus
    }

    fun setMaritalStatus(maritalStatus: String?) {
        this.maritalStatus = maritalStatus
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

    fun getOccupation(): UserOccupation? {
        return occupation
    }

    fun setOccupation(occupation: UserOccupation?) {
        this.occupation = occupation
    }

    fun getQualification(): String? {
        return qualification
    }

    fun setQualification(qualification: String?) {
        this.qualification = qualification
    }
}