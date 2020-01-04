package com.example.pagingsandbox.ui

import android.widget.Filter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.repository.ItemRepository

class StackOverFlowViewModel : ViewModel() {

    val query = MutableLiveData<String>("")

    // TODO 再読み込みではなくRoomにキャッシュ
    val itemPagedList: LiveData<PagedList<Item>> = Transformations.switchMap(query) {
        if (it.isNullOrEmpty()) {
            ItemRepository().getItems()
        } else {
            ItemRepository().getItemsWithFilter(it)
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