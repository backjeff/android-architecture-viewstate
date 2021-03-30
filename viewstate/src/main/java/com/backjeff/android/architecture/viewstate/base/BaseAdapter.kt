package com.backjeff.android.architecture.viewstate.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Type, Binding, ViewHolder : BaseViewHolder<Type>> : RecyclerView.Adapter<ViewHolder>() {

    private var itemClickListener: OnAdapterItemClickListener<Type> = {}

    var items: MutableList<Type> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract fun createViewHolderInstance(view: Binding): ViewHolder

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.setOnClickListener {
            itemClickListener(item)
        }

        holder.bind(item)
    }

    fun setOnItemClickListener(listener: OnAdapterItemClickListener<Type>) {
        itemClickListener = listener
    }
}

abstract class BaseViewHolder<Type>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: Type)
}

typealias OnAdapterItemClickListener<Type> = (Type) -> Unit
