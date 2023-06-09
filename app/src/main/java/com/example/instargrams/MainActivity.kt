package com.example.instargrams

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instargrams.adapters.SearchAdapter
import com.example.instargrams.databinding.ActivityMainBinding
import com.example.instargrams.models.SearchUser
import com.example.instargrams.responses.ItemsItem
import com.example.instargrams.responses.SearchResponse
import com.example.instargrams.utilities.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        hideProgressBar()

        setContentView(view)
    }

    fun setSearchUsersData(items : List<ItemsItem>){
        val users = ArrayList<SearchUser>()

        for (item in items) {
            users.add(
                item.toUser()
            )
        }

        adapter = SearchAdapter(users, true)
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.adapter = adapter
    }

    fun showProgressBar(){
        binding.rvSearch.adapter = null
        binding.progress.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        binding.progress.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search for users ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgressBar()

                val client = ApiConfig.getApi().searchUsers(query, "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")
                client.enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                if(responseBody.items.isNotEmpty()){
                                    binding.noDataLayout.visibility = View.INVISIBLE
                                    setSearchUsersData(responseBody.items)
                                }else{
                                    binding.rvSearch.adapter = null
                                    binding.noDataLayout.visibility = View.VISIBLE
                                }
                            }
                            else{
                                binding.rvSearch.adapter = null
                                binding.noDataLayout.visibility = View.VISIBLE
                            }
                        } else {
                            Log.e("TESTING", "onFailure: ${response.message()}")
                        }
                        hideProgressBar()
                    }
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.e("TESTING", "onFailure: ${t.message}")
                        hideProgressBar()

                    }
                })

                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }
}