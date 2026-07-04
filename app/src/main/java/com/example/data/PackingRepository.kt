package com.example.data

import kotlinx.coroutines.flow.Flow

class PackingRepository(private val packingDao: PackingDao) {
    fun getItemsBySeason(season: String): Flow<List<PackingItem>> {
        return packingDao.getItemsBySeason(season)
    }

    fun getAllItems(): Flow<List<PackingItem>> {
        return packingDao.getAllItems()
    }

    suspend fun insertItem(item: PackingItem) {
        packingDao.insertItem(item)
    }

    suspend fun insertItems(items: List<PackingItem>) {
        packingDao.insertItems(items)
    }

    suspend fun updateItem(item: PackingItem) {
        packingDao.updateItem(item)
    }

    suspend fun deleteItem(item: PackingItem) {
        packingDao.deleteItem(item)
    }

    suspend fun updateCheckStatus(id: Int, isChecked: Boolean) {
        packingDao.updateCheckStatus(id, isChecked)
    }

    suspend fun resetAllChecks() {
        packingDao.resetAllChecks()
    }

    suspend fun clearCustomItems() {
        packingDao.clearCustomItems()
    }

    suspend fun checkAndPrepopulate() {
        if (packingDao.getCount() == 0) {
            val defaultItems = listOf(
                // Summer
                PackingItem(nameEn = "Passport & Documents", nameKu = "پاسپۆرت و بەڵگەنامەکان", nameAr = "جواز السفر والوثائق", season = "summer", category = "documents"),
                PackingItem(nameEn = "Light Clothes", nameKu = "جلی تەنک و هاوینە", nameAr = "ملابس صيفية خفيفة", season = "summer", category = "clothing"),
                PackingItem(nameEn = "Sunglasses", nameKu = "چاویلکەی خۆر", nameAr = "نظارات شمسية", season = "summer", category = "clothing"),
                PackingItem(nameEn = "Phone Charger", nameKu = "بارگاوی کەرەوە", nameAr = "شاحن الهاتف", season = "summer", category = "electronics"),
                PackingItem(nameEn = "Sunblock", nameKu = "کرێمی دژە خۆر", nameAr = "واقي شمس", season = "summer", category = "toiletries"),
                PackingItem(nameEn = "Toothbrush", nameKu = "فڵچەی ددان", nameAr = "فرشاة أسنان", season = "summer", category = "toiletries"),

                // Winter
                PackingItem(nameEn = "Passport & Documents", nameKu = "پاسپۆرت و بەڵگەنامەکان", nameAr = "جواز السفر والوثائق", season = "winter", category = "documents"),
                PackingItem(nameEn = "Heavy Coats", nameKu = "قەمسەڵە و جلی ئەستوور", nameAr = "معاطف وملابس ثقيلة", season = "winter", category = "clothing"),
                PackingItem(nameEn = "Gloves & Hat", nameKu = "دەستکێش و کڵاو", nameAr = "قفازات وقبعة", season = "winter", category = "clothing"),
                PackingItem(nameEn = "Phone Charger", nameKu = "بارگاوی کەرەوە", nameAr = "شاحن الهاتف", season = "winter", category = "electronics"),
                PackingItem(nameEn = "Umbrella", nameKu = "چەتر", nameAr = "مظلة", season = "winter", category = "other"),
                PackingItem(nameEn = "Toothbrush", nameKu = "فڵچەی ددان", nameAr = "فرشاة أسنان", season = "winter", category = "toiletries")
            )
            packingDao.insertItems(defaultItems)
        }
    }
}
