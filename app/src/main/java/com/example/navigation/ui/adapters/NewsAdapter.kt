package com.example.navigation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.navigation.R
import com.example.navigation.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    //1.Создадим класс viewholder
    inner class NewsViewHolder(view: View) : ViewHolder(view) {
        val image = view.findViewById(R.id.article_image) as ImageView
        val title = view.findViewById(R.id.article_title) as TextView
        val date = view.findViewById(R.id.article_date) as TextView
    }

    //2. Создадим колбек,в который передадим модель.
    // Два метода будут проверять одинаковые ли наши item и одинаковый ли контент в наших item.
    //DiffUtil – служебный класс, созданный для улучшения производительности
    // RecyclerView при обновлении списка.
    //DiffUtil.ItemCallback- это собственный класс, ответственный за вычисление разницы между двумя списками.
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    //AsyncListDiffer заключается в том, что последний работает в фоновом потоке.
    // Это делает его идеальным для продолжительных операций или использования вместе с LiveData.
    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_article, parent, false)
        )
    }

    //ListAdapter содержит данные списка во внутреннем поле с именем currentList.
    // currentList содержит все элементы
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this@apply).load(article.urlToImage).into(holder.image)
            holder.image.clipToOutline = true
            holder.title.text = article.title
            holder.date.text = article.publishedAt
        }
    }

    //Реализуем наш onItemClickListener
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}