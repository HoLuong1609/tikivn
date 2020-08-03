package com.tikivn.presentation.viewmodel

import com.tikivn.data.response.BaseResponse
import com.tikivn.data.response.CategoryResponse

interface OnCategoryResponse {

    fun onCategoryResponse(response: BaseResponse<List<List<CategoryResponse>>>)
}