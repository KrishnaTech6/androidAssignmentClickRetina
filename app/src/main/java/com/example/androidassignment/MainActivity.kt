package com.example.androidassignment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.model.ApiResponse
import com.example.androidassignment.model.Content
import com.example.androidassignment.retrofit.RetrofitClient
import com.example.androidassignment.utility.Utils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val titleList = mutableListOf<String>()
    private var description = ""
    private lateinit var titleAdapter: TitleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.statusBar)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        setupRecyclerView()
        fetchData()
    }

    private fun setupRecyclerView() {
        titleAdapter = TitleAdapter(this@MainActivity)
        binding.rvTitle.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = titleAdapter
        }
    }

    private fun fetchData() {
        RetrofitClient.instance.fetchData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val contentJson = data?.choices?.get(0)?.message?.content ?: "No data"
                    Log.d("MainActivity", "Data fetched successfully: $contentJson")

                    val gson = Gson()
                    val content = gson.fromJson(contentJson, Content::class.java)

                    content.titles.forEach{
                        Log.d("MainActivity", "Title: $it")
                        titleList.add(it)
                    }
                    // Notify adapter of data change
                    titleAdapter.submitList(titleList)

                    //For description
                    description = content.description

                    binding.cardItem2.visibility = View.VISIBLE
                    binding.textDescription.text= description

                    binding.btnCopy2.setOnClickListener {
                        Utils.copyToClipboard(description,this@MainActivity)
                    }
                    binding.btnShare2.setOnClickListener {
                        Utils.shareText(description,this@MainActivity)
                    }
                } else {
                    Log.e("MainActivity", "Error fetching data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("MainActivity", "API call failed: ${t.message}")
            }
        })
    }


}
