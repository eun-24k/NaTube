package com.example.natube.ui.home

import com.example.natube.model.Category
import com.example.natube.model.UnifiedItem

sealed class HomeWidget {
    data class TitleWidget(val title:String): HomeWidget()
    data class CategoryWidget(val mCategories : List<Category>) : HomeWidget()
    data class ListCategoryVideoItemWidget(val mUnifiedItem: List<UnifiedItem>) : HomeWidget()
    data class ListKeywordVideoItemWidget(val mUnifiedItem: List<UnifiedItem>) : HomeWidget()

}