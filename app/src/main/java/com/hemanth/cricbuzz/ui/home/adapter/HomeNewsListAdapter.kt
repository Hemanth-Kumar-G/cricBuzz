package com.hemanth.cricbuzz.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.databinding.HomeNewsItemBinding

/**
 *<h1>HomeNewsListAdapter</h1>
 * <p>this is the adapter class for news feed in HomeActivity</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeNewsListAdapter(private val newsList: ArrayList<NewsResponse.Article>) :
    RecyclerView.Adapter<HomeNewsListAdapter.HomeNewsListViewHolder>() {

    var onItemSelectedListener: ((article: NewsResponse.Article) -> Unit)? = null

    class HomeNewsListViewHolder(val binding: HomeNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewsListViewHolder {
        val binding =
            HomeNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeNewsListViewHolder(binding)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: HomeNewsListViewHolder, position: Int) {
        holder.binding.data = newsList[position]

        holder.binding.root.setOnClickListener {
            onItemSelectedListener?.invoke(newsList[position])
        }
    }
}