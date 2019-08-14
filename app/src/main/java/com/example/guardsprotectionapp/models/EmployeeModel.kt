package com.example.guardsprotectionapp.models

data class EmployeeModel(
    val employeeName: String,
    val employeeSurname: String,
    val employeeFullName: String,
    val employeeSvNr: Long,
    val employeeEmail: String,
    val employeeId: Long,
    val jobOfferId: Long,
    val groups: List<Int>,
    val employeeStatus: StatusModel,
    val status: StatusModel,
    val checkIn: Any?,
    val checkOut: Any?,
    val id: Long,
    val createdBy: Long,
    val modifiedBy: Long,
    val createdDate: String,
    val modifiedDate: String
)

data class EmployeesList(val list: List<EmployeeModel>)