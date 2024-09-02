package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserIdentificationDetail {

    @SerializedName("_id")
    @Expose
    private var id: String? = null

    @SerializedName("personalId")
    @Expose
    private var personalId: String? = null

    @SerializedName("panCard")
    @Expose
    private var panCard: UserPanCard? = null

    @SerializedName("aadharCard")
    @Expose
    private var aadharCard: UserAadharCard? = null

    @SerializedName("passport")
    @Expose
    private var passport: UserPassport? = null

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

    fun getPanCard(): UserPanCard? {
        return panCard
    }

    fun setPanCard(panCard: UserPanCard?) {
        this.panCard = panCard
    }

    fun getAadharCard(): UserAadharCard? {
        return aadharCard
    }

    fun setAadharCard(aadharCard: UserAadharCard?) {
        this.aadharCard = aadharCard
    }

    fun getPassport(): UserPassport? {
        return passport
    }

    fun setPassport(passport: UserPassport?) {
        this.passport = passport
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