package com.example.budilnik.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.budilnik.room.AppDatabase
import com.example.budilnik.room.ListEntity
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository
    val allMovies: LiveData<List<ListEntity>>

    init {
        val listDao = AppDatabase.getInstance(application).listDao()
        repository = MovieRepository(listDao)
        allMovies = repository.allMovies
    }

    fun insert(list: ListEntity) = viewModelScope.launch {
        repository.insert(list)
    }
}