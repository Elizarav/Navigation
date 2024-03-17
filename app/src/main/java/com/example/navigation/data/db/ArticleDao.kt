package com.example.navigation.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.navigation.models.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles" )
    suspend fun getAllArticles() : LiveData<List<Article>>
    //Стратегия в случае конфликтов, будет просто заменяться статья.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)
    @Delete
    suspend fun delete(article: Article)
}