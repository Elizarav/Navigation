package com.example.navigation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.api.NewsRepository
import com.example.navigation.models.NewsResponse
import com.example.navigation.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val searchNewsLiveData: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1

    init {
        getSearchNews("")
    }

    fun getSearchNews(query: String) {
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resources.Loading())
            val response = repository.getSearchNews(query = query, pageNumber = searchNewsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    searchNewsLiveData.postValue(Resources.Success(res))
                }
            } else {
                searchNewsLiveData.postValue(Resources.Error(message = response.message()))
            }
        }
    }
}