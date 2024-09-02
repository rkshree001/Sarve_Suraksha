package com.bnet.sarvesuraksha.model

class MyPolicyDetailedData(
    private var imageResId: Int,
    private var policyName: String,
    private var policyNo: String,
    private var dueDate: String,
    private var coverageAmount: String,
    private var premiumAmount: String,
    private var headerText: String
) {

    fun getHeaderText(): String {
        return headerText
    }

    fun setHeaderText(headerText: String) {
        this.headerText = headerText
    }

    fun getImageResId(): Int {
        return imageResId
    }

    fun setImageResId(imageResId: Int) {
        this.imageResId = imageResId
    }

    fun getPolicyName(): String {
        return policyName
    }

    fun setPolicyName(policyName: String) {
        this.policyName = policyName
    }

    fun getPolicyNo(): String {
        return policyNo
    }

    fun setPolicyNo(policyNo: String) {
        this.policyNo = policyNo
    }

    fun getDueDate(): String {
        return dueDate
    }

    fun setDueDate(dueDate: String) {
        this.dueDate = dueDate
    }

    fun getCoverageAmount(): String {
        return coverageAmount
    }

    fun setCoverageAmount(coverageAmount: String) {
        this.coverageAmount = coverageAmount
    }

    fun getPremiumAmount(): String {
        return premiumAmount
    }

    fun setPremiumAmount(premiumAmount: String) {
        this.premiumAmount = premiumAmount
    }
}