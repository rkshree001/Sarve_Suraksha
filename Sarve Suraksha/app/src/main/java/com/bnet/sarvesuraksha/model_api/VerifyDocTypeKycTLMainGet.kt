package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyDocTypeKycTLMainGet {
    @SerializedName("quoteId")
    @Expose
    var quoteId: String? = null

    @SerializedName("docDetail")
    @Expose
    var docDetail: VerifyDocTypeKycTLDocDetail? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("perposerDetail")
    @Expose
    var perposerDetail: VerifyDocTypeKycTLPerposerDetail? = null

    @SerializedName("addressDetail")
    @Expose
    var addressDetail: VerifyDocTypeKycTLAddressDetail? = null

    @SerializedName("contactDetail")
    @Expose
    var contactDetail: VerifyDocTypeKycTLContactDetail? = null

    @SerializedName("memberDetail")
    @Expose
    var memberDetail: VerifyDocTypeKycTLmemberDetailDetail? = null
    @SerializedName("additionalDetail")
    @Expose
    var additionalDetail: VerifyDocTypeKycTLadditionalDetail? = null
}