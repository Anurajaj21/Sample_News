package com.example.news.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.FragmentNewsDetailBinding
import com.example.news.models.NewsModel
import com.example.news.viewmodel.MainViewModel

class NewsDetailFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private var data: NewsModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt("INDEX")
        data = viewModel.getNewsList()[index ?: 0]
        initData()
    }

    private fun initData() {
        binding.title.text = data?.title
        binding.content.text = if (data?.content?.contains('[') == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data?.content?.substringBeforeLast('['), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(data?.content?.substringBeforeLast('['))
            }
        }
        else if(data?.content != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data?.content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(data?.content)
            }
        }
        else{
            ""
        }

        Glide.with(binding.img)
            .load(data?.urlToImage)
            .placeholder(R.color.light_grey)
            .into(binding.img)
    }


    companion object {
        fun getInstance(index: Int): NewsDetailFragment {
            val bundle = Bundle()
            bundle.putInt("INDEX", index)
            val newsFragment = NewsDetailFragment()
            newsFragment.arguments = bundle
            return newsFragment
        }
    }
}