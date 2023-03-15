package com.example.instargrams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.instargrams.adapters.PagerAdapter
import com.example.instargrams.adapters.SearchAdapter
import com.example.instargrams.databinding.ActivityDetailBinding
import com.example.instargrams.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root

        val intent = intent
        val id = intent.getStringExtra("id")

        val pagerAdapter = PagerAdapter(this, id!!)
        binding.viewPager.adapter = pagerAdapter

        setContentView(view)
    }
}