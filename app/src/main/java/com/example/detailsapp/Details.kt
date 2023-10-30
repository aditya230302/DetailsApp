package com.example.detailsapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details")

data class Details(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val name:String,
    val phone:String,
    val age:String
)