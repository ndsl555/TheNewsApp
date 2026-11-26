package com.example.thenewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.FragmentSearchBinding
import com.example.thenewsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
    }
}
