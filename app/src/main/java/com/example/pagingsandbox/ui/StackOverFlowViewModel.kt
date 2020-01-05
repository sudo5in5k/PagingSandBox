package com.example.pagingsandbox.ui

import android.app.Application
import android.widget.Filter
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.repository.ItemRepository

class StackOverFlowViewModel(private val app: Application) : AndroidViewModel(app) {

    val query = MutableLiveData<String>("")
    private val repository: ItemRepository by lazy {
        ItemRepository(
            app.applicationContext,
            viewModelScope
        )
    }

    // TODO 再読み込みではなくRoomにキャッシュ
    val itemPagedList: LiveData<PagedList<Item>> = Transformations.switchMap(query) {
        if (it.isNullOrEmpty()) {
            repository.getItems()
        } else {
            repository.getItemsWithFilter(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun filtering() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return FilterResults().apply {
                //values = filteringItemsByQuery(constraint)
                if (!constraint.isNullOrEmpty()) {
                    query.postValue(constraint.toString())
                } else {
                    query.postValue("")
                }
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            return
        }
    }
}