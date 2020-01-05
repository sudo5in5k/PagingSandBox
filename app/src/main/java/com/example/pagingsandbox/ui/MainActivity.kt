package com.example.pagingsandbox.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.Menu

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pagingsandbox.R
import com.example.pagingsandbox.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val viewModel: StackOverFlowViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(StackOverFlowViewModel::class.java)
    }

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        binding.recycler.setHasFixedSize(true)
        adapter = ItemAdapter()
        binding.recycler.adapter = adapter
        binding.isLoading = true

        viewModel.itemPagedList.observe(this, Observer {
            if (it == null) {
                Log.d("debug", "data is null")
                return@Observer
            }

            try {
                adapter.submitList(it)
                binding.isLoading = false
                Handler().postDelayed({
                    binding.isLoading = false
                }, 4000L)
            } catch (e: Exception) {
                Log.d("debug", "${e.message}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchMenu = menu?.findItem(R.id.action_search)

        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as? SearchManager ?: return false
        searchView = searchMenu?.actionView as? SearchView ?: return false
        searchView.apply {
            inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            isSubmitButtonEnabled = false
            maxWidth = Int.MAX_VALUE
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.filtering().filter(query)
                    clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filtering().filter(query)
                    return false
                }
            })
            setOnCloseListener {
                viewModel.filtering().filter("")
                false
            }
        }
        return true
    }
}
