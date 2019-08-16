package com.example.guardsprotectionapp.utils

import androidx.room.TypeConverter
import com.example.guardsprotectionapp.models.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    val moshi = Moshi.Builder()
        .build()

    @TypeConverter
    fun statusModelToString(statusModel: StatusModel?): String? =
        moshi.adapter(StatusModel::class.java).toJson(statusModel)

    @TypeConverter
    fun stringToStatusModel(value: String): StatusModel? =
        moshi.adapter(StatusModel::class.java).fromJson(value)

    @TypeConverter
    fun offerBannerFileToString(offerBannerFile: OfferBannerFileModel?): String? =
        moshi.adapter(OfferBannerFileModel::class.java).toJson(offerBannerFile)

    @TypeConverter
    fun stringToOfferBannerFile(value: String): OfferBannerFileModel? =
        moshi.adapter(OfferBannerFileModel::class.java).fromJson(value)

    @TypeConverter
    fun assignedEmployeesToString(assignedEmployees: AssignedEmployee?): String? =
        moshi.adapter(AssignedEmployee::class.java).toJson(assignedEmployees)

    @TypeConverter
    fun stringToAssignedEmployee(value: String): AssignedEmployee? =
        moshi.adapter(AssignedEmployee::class.java).fromJson(value)

    @TypeConverter
    fun employeeModelToString(employeeModel: EmployeesList?): String? =
        moshi.adapter(EmployeesList::class.java).toJson(employeeModel)

    @TypeConverter
    fun stringToEmployeeModel(value: String): EmployeesList? =
        moshi.adapter(EmployeesList::class.java).fromJson(value)

    @TypeConverter
    fun employeeListToString(employeeModel: List<EmployeeModel>?): String? {
        val type = Types.newParameterizedType(List::class.java, EmployeeModel::class.java)
        val adapter: JsonAdapter<List<EmployeeModel>> = moshi.adapter(type)
        return adapter.toJson(employeeModel)
    }

    @TypeConverter
    fun stringToEmployeeList(value: String): List<EmployeeModel>? {
        val type = Types.newParameterizedType(List::class.java, EmployeeModel::class.java)
        val adapter: JsonAdapter<List<EmployeeModel>> = moshi.adapter(type)
        return adapter.fromJson(value)
    }
}