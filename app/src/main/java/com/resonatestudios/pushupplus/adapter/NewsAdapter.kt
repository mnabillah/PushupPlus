package com.resonatestudios.pushupplus.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.resonatestudios.pushupplus.R
import com.resonatestudios.pushupplus.model.Article
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<NewsAdapter.Row>() {
    var articles: ArrayList<Article> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Row {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return Row(view)
    }

    override fun onBindViewHolder(holder: Row, position: Int) {
        val article = articles[position]
        holder.newsCard.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(article.url)
            context.startActivity(openUrl)
        }
        Picasso.get().load(article.urlToImage).into(holder.imageViewImage)
        holder.textViewTitle.text = article.title!!
        holder.textViewSource.text = article.source!!.name
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class Row internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsCard: MaterialCardView = itemView.findViewById(R.id.news_card)
        var imageViewImage: ImageView = itemView.findViewById(R.id.news_image)
        var textViewTitle: TextView = itemView.findViewById(R.id.news_title)
        var textViewSource: TextView = itemView.findViewById(R.id.news_source)

    }

    init {
        articles = ArrayList()
    }
}