package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserAddressDetail {
    @SerializedName("_id")
    @Expose
    private var id: String? = null

    @SerializedName("personalId")
    @Expose
    private var personalId: String? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    @SerializedName("street")
    @Expose
    private var street: String? = null

    @SerializedName("landmark")
    @Expose
    private var landmark: String? = null

    @SerializedName("pincode")
    @Expose
    private var pincode: String? = null

    @SerializedName("city")
    @Expose
    private var city: String? = null

    @SerializedName("state")
    @Expose
    private var state: String? = null

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

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getStreet(): String? {
        return street
    }

    fun setStreet(street: String?) {
        this.street = street
    }

    fun getLandmark(): String? {
        return landmark
    }

    fun setLandmark(landmark: String?) {
        this.landmark = landmark
    }

    fun getPincode(): String? {
        return pincode
    }

    fun setPincode(pincode: String?) {
        this.pincode = pincode
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getState(): String? {
        return state
    }

    fun setState(state: String?) {
        this.state = state
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