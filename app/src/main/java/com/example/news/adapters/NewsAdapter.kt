package com.example.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.LayoutNewsCardBinding
import com.example.news.models.NewsModel
import com.google.android.material.card.MaterialCardView

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.VH>() {

    private var list: ArrayList<NewsModel> = arrayListOf()

    fun setData(data: ArrayList<NewsModel>) {
        list = data
        notifyDataSetChanged()
    }

    var onClick: (Int) -> Unit = { _ -> }

    class VH(val binding: LayoutNewsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsModel) {
            binding.title.text = news.title
            binding.subTitle.text = news.description

            Glide.with(binding.img)
                .load(news.urlToImage)
                .placeholder(R.color.light_grey)
                .into(binding.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
        holder.binding.root.setOnClickListener { onClick(position) }
    }

    override fun getItemCount(): Int = list.size
}