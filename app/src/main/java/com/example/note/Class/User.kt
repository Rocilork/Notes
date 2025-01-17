package com.example.note.Class

import kotlinx.serialization.Serializable

@Serializable
data class User (val ID_пользователя: String = "", val ФИО: String = "")