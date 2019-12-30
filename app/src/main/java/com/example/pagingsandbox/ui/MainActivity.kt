package com.example.pagingsandbox.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pagingsandbox.R
import com.example.pagingsandbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ItemAdapter
    private val viewModel: StackOverFlowViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(StackOverFlowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        binding.recycler.setHasFixedSize(true)
        adapter = ItemAdapter()
        binding.recycler.adapter = adapter
        binding.isLoading = true

        viewModel.itemPagedList?.observe(this, Observer {
            if (it == null) {
                Log.d("debug", "data is null")
            } else {
                adapter.submitList(it)
                Handler().postDelayed({
                    binding.isLoading = false
                }, 4000L)
            }
        })
    }
}
