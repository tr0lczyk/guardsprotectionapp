package com.example.guardsprotectionapp.models

import com.squareup.moshi.Json

data class OfferModel (
    val customer: String?,
    val customerId: Int?,
    val customerName: String?,
    val startDate: String,
    val endDate: String?,
    val location: String?,
    val addressPostalCode: Int?,
    val addressCity: String?,
    val addressHouseNumber: String?,
    val fullAddress: String?,
    val status: StatusModel?,
    @Json(name = "break") val breakKindOf: StatusModel?,
    val typeOfEnd: StatusModel?,
    val requiredUniform: String?,
    val additionalInformation: String?,
    val offerBannerFile: OfferBannerFileModel?,
    val groups: String?,
    val assignedEmployees: List<EmployeeModel>?,
    val id: Long?,
    val createdBy: Long?,
    val modifiedBy: Long?,
    val createdDate: String?,
    val modifiedDate: String?
)