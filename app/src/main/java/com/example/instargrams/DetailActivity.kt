package com.example.instargrams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.example.instargrams.adapters.PagerAdapter
import com.example.instargrams.databinding.ActivityDetailBinding
import com.example.instargrams.responses.DetailResponse
import com.example.instargrams.utilities.ApiConfig
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root

        val intent = intent
        val username = intent.getStringExtra("username")

        val pagerAdapter = PagerAdapter(this, username!!)
        binding.viewPager.adapter = pagerAdapter

        fetchUserDetail(username)

        setContentView(view)
    }

    private fun fetchUserDetail(username: String){
        val client = ApiConfig.getApi().userDetail(username, "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")
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.apply {
                            binding.username.text = responseBody.login
                            Picasso.get().load(responseBody.avatarUrl).into(avatarUrl)

                            if(responseBody.name != null)
                                name.text = responseBody.name
                            else
                                name.text = responseBody.login
                        }

                        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                            if(position == 0)
                                tab.text = resources.getString(TAB_TITLES[position]) + " ("+responseBody.following+")"
                            else
                                tab.text = resources.getString(TAB_TITLES[position]) + " ("+responseBody.followers+")"
                        }.attach()
                    }
                } else {
                    Log.e("TESTING", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("TESTING", "onFailure: ${t.message}")
            }
        })
    }
}