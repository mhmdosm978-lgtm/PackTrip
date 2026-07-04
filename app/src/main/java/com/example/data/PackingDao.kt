package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PackingDao {
    @Query("SELECT * FROM packing_items ORDER BY id ASC")
    fun getAllItems(): Flow<List<PackingItem>>

    @Query("SELECT * FROM packing_items WHERE season = :season ORDER BY id ASC")
    fun getItemsBySeason(season: String): Flow<List<PackingItem>>

    @Query("SELECT COUNT(*) FROM packing_items")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PackingItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<PackingItem>)

    @Update
    suspend fun updateItem(item: PackingItem)

    @Delete
    suspend fun deleteItem(item: PackingItem)

    @Query("UPDATE packing_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateCheckStatus(id: Int, isChecked: Boolean)

    @Query("DELETE FROM packing_items WHERE isCustom = 1")
    suspend fun clearCustomItems()

    @Query("UPDATE packing_items SET isChecked = 0")
    suspend fun resetAllChecks()
}
