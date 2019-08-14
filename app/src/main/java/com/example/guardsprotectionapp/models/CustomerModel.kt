package com.example.guardsprotectionapp.models
import com.squareup.moshi.Json


data class CustomerModel(
    @Json(name = "address")
    val address: String?,
    @Json(name = "addressCity")
    val addressCity: String?,
    @Json(name = "addressHouseNumber")
    val addressHouseNumber: String?,
    @Json(name = "addressPostalCode")
    val addressPostalCode: String?,
    @Json(name = "alarm")
    val alarm: Boolean?,
    @Json(name = "assignedOffers")
    val assignedOffers: List<AssignedOffer?>?,
    @Json(name = "auto")
    val auto: Boolean?,
    @Json(name = "birthdate")
    val birthdate: String?,
    @Json(name = "birthdateDisplay")
    val birthdateDisplay: String?,
    @Json(name = "birthplace")
    val birthplace: String?,
    @Json(name = "bl")
    val bl: Boolean?,
    @Json(name = "bsw")
    val bsw: Boolean?,
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "eh")
    val eh: Boolean?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "ex")
    val ex: Boolean?,
    @Json(name = "firebaseMobileAppToken")
    val firebaseMobileAppToken: String?,
    @Json(name = "fs")
    val fs: Boolean?,
    @Json(name = "fullName")
    val fullName: String?,
    @Json(name = "groups")
    val groups: List<Int?>?,
    @Json(name = "hiredTo")
    val hiredTo: String?,
    @Json(name = "hiredToAe")
    val hiredToAe: String?,
    @Json(name = "hiredToEh")
    val hiredToEh: String?,
    @Json(name = "hund")
    val hund: Boolean?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nationality")
    val nationality: String?,
    @Json(name = "oldPassword")
    val oldPassword: String?,
    @Json(name = "optik")
    val optik: Boolean?,
    @Json(name = "password")
    val password: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "picture")
    val picture: Picture?,
    @Json(name = "qso")
    val qso: Boolean?,
    @Json(name = "role")
    val role: String?,
    @Json(name = "surname")
    val surname: String?,
    @Json(name = "svnr")
    val svnr: Int?,
    @Json(name = "vrk")
    val vrk: Boolean?,
    @Json(name = "wp")
    val wp: Boolean?,
    @Json(name = "zu")
    val zu: Boolean?
)

data class AssignedOffer(
    @Json(name = "additionalInformation")
    val additionalInformation: String?,
    @Json(name = "addressCity")
    val addressCity: String?,
    @Json(name = "addressHouseNumber")
    val addressHouseNumber: String?,
    @Json(name = "addressPostalCode")
    val addressPostalCode: String?,
    @Json(name = "assignedEmployees")
    val assignedEmployees: List<AssignedEmployee?>?,
    @Json(name = "break")
    val breakX: Break?,
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "customer")
    val customer: Customer?,
    @Json(name = "customerId")
    val customerId: Int?,
    @Json(name = "customerName")
    val customerName: String?,
    @Json(name = "endDate")
    val endDate: String?,
    @Json(name = "fullAddress")
    val fullAddress: String?,
    @Json(name = "groups")
    val groups: List<Group?>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "offerBannerFile")
    val offerBannerFile: OfferBannerFile?,
    @Json(name = "requiredUniform")
    val requiredUniform: RequiredUniform?,
    @Json(name = "startDate")
    val startDate: String?,
    @Json(name = "status")
    val status: Status?,
    @Json(name = "typeOfEnd")
    val typeOfEnd: TypeOfEnd?
)

data class RequiredUniform(
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "name")
    val name: String?
)

data class Group(
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "name")
    val name: String?
)

data class AssignedEmployee(
    @Json(name = "checkIn")
    val checkIn: String?,
    @Json(name = "checkOut")
    val checkOut: String?,
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "employeeEmail")
    val employeeEmail: String?,
    @Json(name = "employeeFullName")
    val employeeFullName: String?,
    @Json(name = "employeeId")
    val employeeId: Int?,
    @Json(name = "employeeName")
    val employeeName: String?,
    @Json(name = "employeeStatus")
    val employeeStatus: EmployeeStatus?,
    @Json(name = "employeeSurname")
    val employeeSurname: String?,
    @Json(name = "employeeSvNr")
    val employeeSvNr: Int?,
    @Json(name = "groups")
    val groups: List<Int?>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "jobOfferId")
    val jobOfferId: Int?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?,
    @Json(name = "status")
    val status: Status?
)

data class Status(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

data class EmployeeStatus(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

data class Break(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

data class TypeOfEnd(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

data class Customer(
    @Json(name = "address")
    val address: String?,
    @Json(name = "addressCity")
    val addressCity: String?,
    @Json(name = "addressHouseNumber")
    val addressHouseNumber: String?,
    @Json(name = "addressPostalCode")
    val addressPostalCode: String?,
    @Json(name = "assignedOffers")
    val assignedOffers: List<Any?>?,
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
    val status: String?,
    @Json(name = "vatEu")
    val vatEu: String?
)

data class Logo(
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "fileData")
    val fileData: String?,
    @Json(name = "fileExtension")
    val fileExtension: String?,
    @Json(name = "fileName")
    val fileName: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "isDeleted")
    val isDeleted: Boolean?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?
)

data class OfferBannerFile(
    @Json(name = "createdBy")
    val createdBy: Int?,
    @Json(name = "createdDate")
    val createdDate: String?,
    @Json(name = "fileData")
    val fileData: String?,
    @Json(name = "fileExtension")
    val fileExtension: String?,
    @Json(name = "fileName")
    val fileName: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "isDeleted")
    val isDeleted: Boolean?,
    @Json(name = "modifiedBy")
    val modifiedBy: Int?,
    @Json(name = "modifiedDate")
    val modifiedDate: String?
)