package com.example.guardsprotectionapp.models

data class OfferBannerFileModel (
    val id: Long,
    val isDeleted: Boolean,
    val createdBy: Long,
    val modified: Long,
    val createdDate: String,
    val modifiedDate: String,
    val fileName: String,
    val fileData: String,
    val fileExtension: String
)