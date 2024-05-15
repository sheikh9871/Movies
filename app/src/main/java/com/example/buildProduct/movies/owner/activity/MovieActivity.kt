package com.example.buildProduct.movies.owner.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buildProduct.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_activity_layout)
    }
}