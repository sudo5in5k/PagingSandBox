package com.example.pagingsandbox.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingsandbox.BR
import com.example.pagingsandbox.data.entity.QiitaUserEntity
import com.example.pagingsandbox.databinding.ItemBinding

class QiitaUserAdapter : PagingDataAdapter<QiitaUserEntity, QiitaUserAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding?.setVariable(BR.entity, getItem(position))
        holder.binding?.executePendingBindings()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QiitaUserEntity>() {
            override fun areItemsTheSame(oldItem: QiitaUserEntity, newItem: QiitaUserEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: QiitaUserEntity, newItem: QiitaUserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}