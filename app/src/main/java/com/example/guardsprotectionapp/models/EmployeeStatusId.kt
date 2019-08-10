package com.example.guardsprotectionapp.models

enum class EmployeeStatusId(val status: Int) {
    NOTSEND(100),
    INBOX(1),
    ACCEPTED(0),
    DECLINED(2)
}