package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAddedFamilyMemberMainRes {
    @SerializedName("personalDetail")
    @Expose
    var personalDetail: GetAddedFamilyMemberPersonalDetail? = null

    @SerializedName("profilePicture")
    @Expose
    var profilePicture: UserProfilePicture? = null

    @SerializedName("contactDetail")
    @Expose
    var contactDetail: UserContactDetail? = null

    @SerializedName("addressDetail")
    @Expose
    var addressDetail: UserAddressDetail? = null
}
