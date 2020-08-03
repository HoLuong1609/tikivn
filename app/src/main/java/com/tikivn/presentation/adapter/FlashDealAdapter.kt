package com.tikivn.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.tikivn.BR
import com.tikivn.R
import com.tikivn.databinding.ItemFlashDealBinding
import com.tikivn.presentation.base.BaseBindingAdapter
import com.tikivn.presentation.base.BaseBindingViewHolder
import com.tikivn.presentation.uimodel.FlashDealUiModel
import com.tikivn.presentation.viewmodel.HomeViewModel

class FlashDealAdapter(
    var viewModel: HomeViewModel,
    var parentLifecycleOwner: LifecycleOwner
) :
    BaseBindingAdapter<FlashDealUiModel, FlashDealAdapter.FlashDealHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashDealHolder =
        FlashDealHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_flash_deal,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FlashDealHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun setData(data: List<FlashDealUiModel>) {
        submitList(data)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<FlashDealUiModel>() {
        override fun areItemsTheSame(
            oldItem: FlashDealUiModel,
            newItem: FlashDealUiModel
        ): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(
            oldItem: FlashDealUiModel,
            newItem: FlashDealUiModel
        ) = oldItem.imageUrl == newItem.imageUrl
    }

    inner class FlashDealHolder(private var viewDataBinding: ItemFlashDealBinding) :
        BaseBindingViewHolder<FlashDealUiModel>(viewDataBinding) {
        override fun bind(item: FlashDealUiModel) {
            viewDataBinding.apply {
                setVariable(BR.flashDeal, item)
                lifecycleOwner = parentLifecycleOwner
                executePendingBindings()
            }
        }
    }
}