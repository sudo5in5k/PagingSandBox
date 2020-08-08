package com.example.pagingsandbox.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pagingsandbox.R
import com.example.pagingsandbox.databinding.ActivityMainBinding
import com.example.pagingsandbox.viewmodel.QiitaUserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QiitaUserAdapter
    private lateinit var viewModel: QiitaUserViewModel

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider.NewInstanceFactory().create(QiitaUserViewModel::class.java)
        adapter = QiitaUserAdapter()

        binding.apply {
            recycler.layoutManager = GridLayoutManager(this@MainActivity, 2)
            recycler.setHasFixedSize(true)
            recycler.adapter = this@MainActivity.adapter
            viewModel = this@MainActivity.viewModel
        }

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
        }

        viewModel.data.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}
