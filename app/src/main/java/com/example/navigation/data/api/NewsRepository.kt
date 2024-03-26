package com.example.navigation.data.api

import java.util.Locale.IsoCountryCode
import javax.inject.Inject

// В него инжектим наш сервис
class NewsRepository @Inject constructor(private val newsService: NewsService) {
    //Создадим суспенд функцию, которая будет возвращать все наши айтлайнцы
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(countryCode = countryCode, page = pageNumber)

    //Создадим суспенд функцию, которая возвращает список новостей, содержащих наш ключ
    suspend fun getSearchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)
}