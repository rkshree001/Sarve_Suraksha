package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GetUserDeatilsMainRes {

    @SerializedName("personalDetail")
    @Expose
    private var personalDetail: UserPersonalDetail? = null

    @SerializedName("profilePicture")
    @Expose
    private var profilePicture: UserProfilePicture? = null

    @SerializedName("contactDetail")
    @Expose
    private var contactDetail: UserContactDetail? = null

    @SerializedName("addressDetail")
    @Expose
    private var addressDetail: UserAddressDetail? = null

    @SerializedName("identificationDetail")
    @Expose
    private var identificationDetail: UserIdentificationDetail? = null

    @SerializedName("advanceDetail")
    @Expose
    private var advanceDetail: UserAdvanceDetail? = null

    fun getPersonalDetail(): UserPersonalDetail? {
        return personalDetail
    }

    fun setPersonalDetail(personalDetail: UserPersonalDetail?) {
        this.personalDetail = personalDetail
    }

    fun getProfilePicture(): UserProfilePicture? {
        return profilePicture
    }

    fun setProfilePicture(profilePicture: UserProfilePicture?) {
        this.profilePicture = profilePicture
    }

    fun getContactDetail(): UserContactDetail? {
        return contactDetail
    }

    fun setContactDetail(contactDetail: UserContactDetail?) {
        this.contactDetail = contactDetail
    }

    fun getAddressDetail(): UserAddressDetail? {
        return addressDetail
    }

    fun setAddressDetail(addressDetail: UserAddressDetail?) {
        this.addressDetail = addressDetail
    }

    fun getIdentificationDetail(): UserIdentificationDetail? {
        return identificationDetail
    }

    fun setIdentificationDetail(identificationDetail: UserIdentificationDetail?) {
        this.identificationDetail = identificationDetail
    }

    fun getAdvanceDetail(): UserAdvanceDetail? {
        return advanceDetail
    }

    fun setAdvanceDetail(advanceDetail: UserAdvanceDetail?) {
        this.advanceDetail = advanceDetail
    }

}