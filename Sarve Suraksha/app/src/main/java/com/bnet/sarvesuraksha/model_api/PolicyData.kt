package com.bnet.sarvesuraksha.model_api




sealed class PolicyData {
    data class Health(val data: HealthInsuranceRes) : PolicyData()
    data class Travel(val data: TravelInsuranceRes) : PolicyData()
    data class Vehicle(val data: VehicleInsuranceRes) : PolicyData()
}