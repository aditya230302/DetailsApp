package com.example.detailsapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DetailsDAO {

    @Insert
    suspend fun insert(details: Details)

    @Update
    suspend fun update(details: Details)

    @Delete
    suspend fun delete(details: Details)

    @Query("SELECT * FROM details")
    fun getContact() : LiveData<List<Details>>
}