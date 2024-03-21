package com.example.navigation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigation.R
import com.example.navigation.databinding.FragmentFavoriteBinding
import com.example.navigation.databinding.FragmentMainBinding
import com.example.navigation.ui.adapters.NewsAdapter
import com.example.navigation.utils.Resources
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()
//0. Обьявить адаптер
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    //2.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resources.Loading -> {
                    binding.pagProgressBar.visibility = View.VISIBLE
                }

                is Resources.Error -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", "MainFragment error $it")
                    }
                }
            }
        }
    }

    //1. Инициализируем наш адаптер
    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.newsAdapterRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}