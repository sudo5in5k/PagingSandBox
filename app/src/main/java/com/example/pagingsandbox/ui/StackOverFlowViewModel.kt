package com.example.pagingsandbox.ui

import android.app.Application
import android.widget.Filter
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.pagingsandbox.data.remote.Item
import com.example.pagingsandbox.data.repository.ItemRepository

class StackOverFlowViewModel(private val app: Application) : AndroidViewModel(app) {

    val query = MutableLiveData<String>("")

    // TODO 再読み込みではなくRoomにキャッシュ
    var itemPagedList: MutableLiveData<PagedList<Item>> = Transformations.switchMap(query) {
        if (it.isNullOrEmpty()) {
            ItemRepository(app.applicationContext, viewModelScope).getItems()
        } else {
            ItemRepository(app.applicationContext, viewModelScope, true).getItemsWithFilter(it)
        }
    } as MutableLiveData<PagedList<Item>>

    fun refresh() {
        itemPagedList.postValue(itemPagedList.value)
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