package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packing_items")
data class PackingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val season: String, // "summer", "winter"
    val isChecked: Boolean = false,
    val isCustom: Boolean = false,
    val category: String = "other",
    val photoUri: String? = null
)
