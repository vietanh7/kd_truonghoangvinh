package com.example.reliatest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reliatest.databinding.ItemProductBinding
import com.example.reliatest.model.Product

class ProductAdapter(
     val editListener: (product: Product) -> Unit,
     val deleteListener: (product: Product) -> Unit,
) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), editListener, deleteListener)
    }

    class ProductViewHolder private constructor(
        val binding: ItemProductBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: Product,
            editListener: (product: Product) -> Unit,
            deleteListener: (product: Product) -> Unit,
        ) {
            binding.product = product
            binding.tvEdit.setOnClickListener {
                editListener.invoke(product)
            }
            binding.tvDelete.setOnClickListener {
                deleteListener.invoke(product)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding, parent.context)
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

}