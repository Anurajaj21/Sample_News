package com.example.news.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapters.NewsAdapter
import com.example.news.databinding.FragmentNewsListBinding
import com.example.news.models.ResultHandler
import com.example.news.viewmodel.MainViewModel


class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initNewsRv()
        initListener()
        getData()
    }

    private fun initObserver() {
        viewModel.responseLiveData.observe(viewLifecycleOwner){
            when(it){
                is ResultHandler.Loading -> {
                    binding.pb.visibility = View.VISIBLE
                }
                is ResultHandler.Success -> {
                    binding.pb.visibility = View.INVISIBLE
                    if (!it.result.isNullOrEmpty()){
                        viewModel.setNewsList(it.result)
                        adapter.setData(it.result)
                    }
                    else{
                        binding.errorTv.visibility = View.VISIBLE
                    }
                }
                is ResultHandler.Failure -> {
                    binding.pb.visibility = View.INVISIBLE
                    binding.errorTv.text = "something went wrong!"
                    binding.errorTv.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getData() {
        viewModel.getNews()
    }

    private fun initListener() {

        binding.btnRetry.setOnClickListener {
            binding.btnRetry.visibility = View.INVISIBLE
            binding.errorTv.visibility = View.INVISIBLE
            getData()
        }

        adapter.onClick = {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.frag_container, NewsDetailFragment.getInstance(it))
                ?.addToBackStack("News List")
                ?.commit()
        }
    }

    private fun initNewsRv() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NewsListFragment.adapter
        }
    }
}