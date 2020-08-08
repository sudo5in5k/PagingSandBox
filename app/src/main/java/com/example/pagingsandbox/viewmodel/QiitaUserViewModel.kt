package com.example.pagingsandbox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingsandbox.data.entity.QiitaUserEntity
import com.example.pagingsandbox.data.paging.ItemDataSource
import com.example.pagingsandbox.data.remote.QiitaUserService
import com.example.pagingsandbox.data.repository.QiitaUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class QiitaUserViewModel : ViewModel() {

    private val api = QiitaUserService()
    private val source = ItemDataSource(api)
    private val repository = QiitaUserRepository(source)

    private val itemFlow = repository.fetchDataFlow().cachedIn(viewModelScope)

    private val _data = MutableLiveData<PagingData<QiitaUserEntity>>()
    val data: LiveData<PagingData<QiitaUserEntity>>
        get() = _data

    init {
        viewModelScope.launch {
            itemFlow.collectLatest {
                _data.value = it
            }
        }
    }
}