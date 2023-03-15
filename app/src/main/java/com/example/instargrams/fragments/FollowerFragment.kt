package com.example.instargrams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instargrams.adapters.SearchAdapter
import com.example.instargrams.databinding.FragmentFollowerBinding
import com.example.instargrams.models.SearchUser
import com.example.instargrams.responses.FollowerResponseItem
import com.example.instargrams.responses.FollowingResponseItem
import com.example.instargrams.utilities.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment(private val username: String) : Fragment() {

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        fetchUserFollowers(username)

        return binding.root
    }

    private fun fetchUserFollowers(username: String){
        Log.d("TESTING", username)
        val client = ApiConfig.getApi().userFollowers(username, "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")
        client.enqueue(object : Callback<List<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.isNotEmpty()) {
                            Log.d("TESTING", responseBody.toString())
                            setFollowersData(responseBody)
                        }
                    }
                } else {
                    Log.e("TESTING", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e("TESTING", "onFailure: ${t.message}")
            }
        })
    }

    fun setFollowersData(items: List<FollowerResponseItem>){
        val users = ArrayList<SearchUser>()

        for (item in items) {
            users.add(
                item.toUser()
            )
        }

        val adapter = SearchAdapter(users, true)
        binding.rvFollower.layoutManager = LinearLayoutManager(context)
        binding.rvFollower.adapter = adapter
    }
}