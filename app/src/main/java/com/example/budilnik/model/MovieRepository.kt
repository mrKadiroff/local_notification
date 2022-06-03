package com.example.budilnik.model

import androidx.lifecycle.LiveData
import com.example.budilnik.room.ListDao
import com.example.budilnik.room.ListEntity

class MovieRepository(private val listDao: ListDao) {
    val allMovies: LiveData<List<ListEntity>> = listDao.getAll()

    suspend fun insert(list: ListEntity) {
        listDao.insert(list)
    }
}