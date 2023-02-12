package com.example.cleanarchitecture.presentation.main.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitecture.databinding.ItemLayoutBinding
import com.example.cleanarchitecture.domain.model.Product

class ProductListAdapter :
    ListAdapter<Product, ProductListAdapter.ProductListViewHolder>(DiffCallBack()) {

    lateinit var onDeleteClick: (Product) -> Unit
    lateinit var onEditClick: (Product) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductListViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                index.text = adapterPosition.plus(1).toString()
                name.text = product.name
                edit.setOnClickListener {
                    onEditClick(product)
                }
                itemView.setOnClickListener {
                    onDeleteClick(product)
                }
            }
        }
    }
}