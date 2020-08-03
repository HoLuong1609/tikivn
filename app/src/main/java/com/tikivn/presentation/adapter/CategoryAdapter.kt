package com.tikivn.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.tikivn.BR
import com.tikivn.R
import com.tikivn.data.response.CategoryResponse
import com.tikivn.databinding.ItemCategoryBinding
import com.tikivn.presentation.base.BaseBindingAdapter
import com.tikivn.presentation.base.BaseBindingViewHolder
import com.tikivn.presentation.viewmodel.HomeViewModel

class CategoryAdapter(
    var viewModel: HomeViewModel,
    var parentLifecycleOwner: LifecycleOwner
) :
    BaseBindingAdapter<CategoryResponse, CategoryAdapter.CategoryHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder =
        CategoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun setData(data: List<CategoryResponse>) {
        submitList(data)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<CategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ) = oldItem.imageUrl == newItem.imageUrl && oldItem.content == newItem.content
    }

    inner class CategoryHolder(private var viewDataBinding: ItemCategoryBinding) :
        BaseBindingViewHolder<CategoryResponse>(viewDataBinding) {
        override fun bind(item: CategoryResponse) {
            viewDataBinding.apply {
                setVariable(BR.category, item)
                lifecycleOwner = parentLifecycleOwner
                executePendingBindings()
            }
        }
    }
}