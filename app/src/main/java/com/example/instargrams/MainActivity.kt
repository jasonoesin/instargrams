package com.example.instargrams

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root


        setContentView(view)
    }

    fun setSearchUsersData(items : List<ItemsItem>){
        val users = ArrayList<SearchUser>()

        for (item in items) {
            users.add(
                item.toUser()
            )
        }

        val adapter = SearchAdapter(users)
        binding.rvSearch.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search for users ..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                val client = ApiConfig.getApi().searchUsers(newText)
                client.enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                Log.e("TESTING", responseBody.toString())
                                setSearchUsersData(responseBody.items)
                            }
                        } else {
                            Log.e("TESTING", "onFailure: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.e("TESTING", "onFailure: ${t.message}")
                    }
                })
                return false
            }
        })

        return true
    }
}