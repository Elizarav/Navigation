package com.example.navigation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.api.TestRepo
import com.example.navigation.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TestRepo) : ViewModel() {

    private val _all = MutableLiveData<NewsResponse>()
    val all: LiveData<NewsResponse>
        get() = _all

    init {
        getAll()
    }

    private fun getAll() = viewModelScope.launch {
        repository.getAll().let {
            if (it.isSuccessful) {
                _all.postValue(it.body())
            } else {
                Log.d("checkData", "Failed to load articles ${it.errorBody()}")
            }
        }
    }
}