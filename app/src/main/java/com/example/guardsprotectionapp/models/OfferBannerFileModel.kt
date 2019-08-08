package com.example.guardsprotectionapp.models

data class OfferBannerFileModel (
    val id: Long,
    val isDeleted: Boolean,
    val createdBy: Long,
    val modifiedBy: Long,
    val createdDate: String,
    val modifiedDate: String,
    val fileName: String,
    val fileData: String,
    val fileExtension: String
)