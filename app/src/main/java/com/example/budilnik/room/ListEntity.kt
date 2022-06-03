package com.example.budilnik.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movie_items")
data class ListEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "color") var color: String,
    @ColumnInfo(name = "textcolor") var textcolor: String,
    @ColumnInfo(name = "total") var total: Int
):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}