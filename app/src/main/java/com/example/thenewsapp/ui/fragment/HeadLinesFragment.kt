package com.example.thenewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thenewsapp.R
import com.example.thenewsapp.adapter.NewsAdapter
import com.example.thenewsapp.databinding.FragmentHeadlinesBinding
import com.example.thenewsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.thenewsapp.util.Resource
import com.example.thenewsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeadLinesFragment : Fragment(R.layout.fragment_headlines) {
    private val viewModel: NewsViewModel by viewModel()
    lateinit var newsAdapter: NewsAdapter
    lateinit var retryButton: Button
    lateinit var errorText: TextView
    lateinit var itemHeadLinesError: CardView
    lateinit var binding: FragmentHeadlinesBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)

        itemHeadLinesError = view.findViewById(R.id.itemHeadlinesError)
        retryButton = itemHeadLinesError.findViewById(R.id.retryButton)
        errorText = itemHeadLinesError.findViewById(R.id.errorText)
        setupHeadLinesRecycler()
        newsAdapter.setOnItemClickListener {
            val bundle =
                Bundle().apply {
                    putSerializable("article", it)
                }
            findNavController().navigate(
                R.id.action_headlinesFragment_to_articleFragment,
                bundle,
            )
        }
        viewModel.headlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.headlinesPage == totalPages
                        if (isLastPage) {
                            binding.recyclerHeadlines.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occured: $it", Toast.LENGTH_LONG).show()
                        showErrorMessage(response.message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                    hideErrorMessage()
                }
            }
        }
        retryButton.setOnClickListener {
            viewModel.getHeadlines("us")
            hideErrorMessage()
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemHeadLinesError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemHeadLinesError.visibility = View.VISIBLE
        isError = true
        errorText.text = message
    }

    private fun setupHeadLinesRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HeadLinesFragment.scrollListener)
        }
    }

    val scrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val isNoError = !isError
                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

                // 該翻頁
                val shouldPaginate =
                    isNoError &&
                        isNotLoadingAndNotLastPage &&
                        isAtLastItem &&
                        isNotAtBeginning &&
                        isTotalMoreThanVisible &&
                        isScrolling

                if (shouldPaginate) {
                    viewModel.getHeadlines("us")
                    isScrolling = false
                }
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        }
}
