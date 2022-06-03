package com.example.budilnik.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListDao {

    @Query("select * from movie_items")
    fun getAll(): LiveData<List<ListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listEntity: ListEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert2(listEntity: ListEntity)


}