package com.example.navigation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.api.NewsRepository
import com.example.navigation.data.api.NewsService
import com.example.navigation.models.NewsResponse
import com.example.navigation.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Response
import java.util.Locale.IsoCountryCode
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    val newsLiveData: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("ru")
    }
    //получаем список всех наших новостей и получаем данные из сервера
    private fun getNews(countryCode: String) =
        viewModelScope.launch {
            newsLiveData.postValue(Resources.Loading())
            val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response.isSuccessful) {
                response.body().let { res ->
                    newsLiveData.postValue(Resources.Success(res))
                }
            } else {
                newsLiveData.postValue(Resources.Error(message = response.message()))
            }
        }
}