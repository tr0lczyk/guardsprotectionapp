package com.example.guardsprotectionapp.models
import com.squareup.moshi.Json


data class CustomersRequest(
    @Json(name = "address")
    val address: String?,
    @Json(name = "addressCity")
    val addressCity: String?,
    @Json(name = "addressHouseNumber")
    val addressHouseNumber: String?,
    @Json(name = "addressPostalCode")
    val addressPostalCode: String?,
    @Json(name = "assignedOffers")
    val assignedOffers: Any?,
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "fullAddress")
    val fullAddress: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "logo")
    val logo: Logo?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "status")
    val status: Int?,
    @Json(name = "vatEu")
    val vatEu: String?
)