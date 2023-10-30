package com.example.detailsapp

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Details::class], version = 2)
abstract class DetailsDatabase: RoomDatabase() {

    abstract fun DetailsDAO(): DetailsDAO //Data Access Objects

}

