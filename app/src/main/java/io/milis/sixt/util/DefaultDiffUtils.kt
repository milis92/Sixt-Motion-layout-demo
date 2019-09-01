package io.milis.sixt.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultDiffUtils<T> : DiffUtil.ItemCallback<T>(){

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
       return oldItem == newItem
    }
}