package com.example.natube.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hwangtube.network.RetrofitInstance
import com.example.natube.model.UnifiedItem
import com.example.natube.model.searchmodel.SearchModel
import com.example.natube.network.YoutubeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchViewModel : ViewModel() {
    private val searchResults = MutableLiveData<List<UnifiedItem>>()
    private val youtubeAPI: YoutubeAPI = RetrofitInstance.api

    fun getSearchResults(): LiveData<List<UnifiedItem>> {
        return searchResults
    }

    suspend fun searchVideos(query: String, apiKey: String) {
        try {
            val searchModel: SearchModel = withContext(Dispatchers.IO) {
                youtubeAPI.getSearchingVideos("snippet", 10, "KR", apiKey, query,"")
            }

            if (searchModel.items != null) {
                val items = extractItemsFromSearchResponse(searchModel)
                searchResults.postValue(items)
            } else {

            }
        } catch (e: HttpException) {
            //  실패 시 처리

        } catch (e: Exception) {
            // 기타 예외 처리
        }
    }

    private fun extractItemsFromSearchResponse(response: SearchModel?): List<UnifiedItem> {
        val items = mutableListOf<UnifiedItem>()
        response?.items?.forEach { searchItem ->
            val item = UnifiedItem(
                searchItem.snippet.title,
                searchItem.snippet.channelTitle,
                searchItem.snippet.description,
                searchItem.snippet.publishedAt,
                searchItem.snippet.thumbnails.default.url,
                searchItem.snippet.publishTime
            )
            items.add(item)
        }
        return items
    }
}
