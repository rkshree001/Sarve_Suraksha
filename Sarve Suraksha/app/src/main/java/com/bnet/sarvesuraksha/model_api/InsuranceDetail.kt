package com.bnet.sarvesuraksha.model_api

data class InsuranceDetail(
    val insuranceType: String,
    val insurerName: String,
    val policyNumber: String,
    val policyExpiryDate: String
)