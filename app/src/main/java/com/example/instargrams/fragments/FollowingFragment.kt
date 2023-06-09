package com.example.instargrams.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instargrams.adapters.SearchAdapter
import com.example.instargrams.databinding.FragmentFollowingBinding
import com.example.instargrams.models.SearchUser
import com.example.instargrams.responses.*
import com.example.instargrams.utilities.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment(private val username: String) : Fragment() {


    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        fetchUserFollowings(username)

        showProgressBar()

        return binding.root
    }

    private fun showProgressBar(){
        binding.rvFollowing.adapter = null
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progress.visibility = View.GONE
    }

    private fun fetchUserFollowings(username: String){
        Log.d("TESTING", username)
        val client = ApiConfig.getApi().userFollowings(username, "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")
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        if (responseBody.isNotEmpty()) {
                            Log.d("TESTING", responseBody.toString())
                            binding.noDataLayout.visibility = View.INVISIBLE
                            setFollowingData(responseBody)
                        }
                        else{
                            binding.rvFollowing.adapter = null
                            binding.noDataLayout.visibility = View.VISIBLE
                        }
                    }
                } else {
                    Log.e("TESTING", "onFailure1: ${response.message()}")
                }

                hideProgressBar()
            }
            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                Log.e("TESTING", "onFailure2: ${t.message}")

                hideProgressBar()
            }
        })
    }

    fun setFollowingData(items: List<FollowingResponseItem>){
        val users = ArrayList<SearchUser>()

        for (item in items) {
            users.add(
                item.toUser()
            )
        }

        val adapter = SearchAdapter(users, true)
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.adapter = adapter
    }
}