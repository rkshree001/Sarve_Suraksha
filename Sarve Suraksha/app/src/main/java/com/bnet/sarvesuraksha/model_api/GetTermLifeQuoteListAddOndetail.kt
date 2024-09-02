package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetTermLifeQuoteListAddOndetail {
    @SerializedName("addOnName")
    @Expose
    var addOnName: String? = null

    @SerializedName("premium")
    @Expose
    var premium: Int? = null

    @SerializedName("declaration")
    @Expose
    var declaration: String? = null

    @SerializedName("sumAssured")
    @Expose
    var sumAssured: GetTermLifeQuoteListSumAssured? = null

    @SerializedName("coverUpto")
    @Expose
    var coverUpto: Int? = null
}
