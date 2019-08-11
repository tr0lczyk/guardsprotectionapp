package com.example.guardsprotectionapp.models

import com.example.guardsprotectionapp.utils.Utilities
import java.util.*
import kotlin.collections.ArrayList

data class EmployeeStatusModel(
    val jobOfferId: Long,
    val employeeId: Long,
    val employeeStatusId: Int,
    val multipleEmployeeId: List<Long> = listOf(0),
    val statusId: Long = 0,
    val operationDate: String = Utilities.convertDateToString(Date())
)