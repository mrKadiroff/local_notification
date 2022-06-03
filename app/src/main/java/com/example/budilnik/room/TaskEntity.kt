package com.example.budilnik.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "vazifa")
    var task: String? = null

    @ColumnInfo(name = "kalendar")
    var calendar: String? = null

    @ColumnInfo(name = "vaqt")
    var taskId: Int? = null

    @ColumnInfo(name = "list")
    var list_name: String? = null

    @ColumnInfo(name = "time")
    var time: String? = null
}