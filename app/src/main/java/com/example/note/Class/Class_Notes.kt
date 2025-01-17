package com.example.note.Class

import kotlinx.serialization.Serializable

@Serializable
data class Class_Notes (val ID_заметки: Int, val ДляЧего: String = "", val Описание: String = "", val id_пользователя: String = "")