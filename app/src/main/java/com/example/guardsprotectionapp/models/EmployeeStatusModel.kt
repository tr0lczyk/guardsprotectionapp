package com.example.guardsprotectionapp.models

import com.example.guardsprotectionapp.utils.Utilities
import java.util.*

data class EmployeeStatusModel(
    val jobOfferId: Long,
    val employeeId: Long,
    val multipleEmployeeId: List<Long>,
    val statusId: Long,
    val employeeStatusId: EmployeeStatusId,
    val operationDate: String = Utilities.convertDateToString(Date())
)