package com.bnet.sarvesuraksha.model_api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostSummaryDetailsTL(
    @SerializedName("sumAssured")
    @Expose
    var sumAssured: Int? = null,

    @SerializedName("coverUpto")
    @Expose
    var coverUpto: Int? = null,

    @SerializedName("paymentType")
    @Expose
    var paymentType: String? = null,

    @SerializedName("paymentTenure")
    @Expose
    var paymentTenure: Int? = null,

    @SerializedName("addOnDetail")
    @Expose
    var addOnDetail: List<String>? = null,

    @SerializedName("totalPremium")
    @Expose
    var totalPremium: Double? = null,

    @SerializedName("GST")
    @Expose
    var gst: Int? = null,

    @SerializedName("gstAmount")
    @Expose
    var gstAmount: Double? = null,

    @SerializedName("payableAmount")
    @Expose
    var payableAmount: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createStringArrayList(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(sumAssured)
        parcel.writeValue(coverUpto)
        parcel.writeString(paymentType)
        parcel.writeValue(paymentTenure)
        parcel.writeStringList(addOnDetail)
        parcel.writeValue(totalPremium)
        parcel.writeValue(gst)
        parcel.writeValue(gstAmount)
        parcel.writeValue(payableAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostSummaryDetailsTL> {
        override fun createFromParcel(parcel: Parcel): PostSummaryDetailsTL {
            return PostSummaryDetailsTL(parcel)
        }

        override fun newArray(size: Int): Array<PostSummaryDetailsTL?> {
            return arrayOfNulls(size)
        }
    }
}
