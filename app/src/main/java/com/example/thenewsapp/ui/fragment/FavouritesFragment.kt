package com.example.thenewsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.FragmentFavoritesBinding
import com.example.thenewsapp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(R.layout.fragment_favorites) {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var binding: FragmentFavoritesBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
    }
}
