package com.tikivn.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tikivn.presentation.binding.BindDataAdapter

abstract class BaseBindingAdapter<M,H: BaseBindingViewHolder<M>>(diffCallback: DiffUtil.ItemCallback<M>): ListAdapter<M, H>(diffCallback),
    BindDataAdapter<List<M>>