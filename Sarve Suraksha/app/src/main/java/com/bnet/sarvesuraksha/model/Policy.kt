package com.bnet.sarvesuraksha.model

data class Policy(
    val imageResId: Int,
    val policyName: String,
    val activePolicyCount: Int,
    val expiredPolicyCount: Int,
    val backgroundTint: Int
)
