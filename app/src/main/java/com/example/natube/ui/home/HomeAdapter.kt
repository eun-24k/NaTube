package com.example.natube.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.natube.databinding.FragmentHomeTitleBinding

/**
 *  HomeFragment 에 화면 구성
 *  1. ItemViewType 찾기
 *  2. 해당 ViewType 에따른 ViewHolder 생성 및 연결
 *  3. 연결된 ViewHolder 에 저장한 값들 Binding
 */
class HomeAdapter : ListAdapter<HomeWidget, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeWidget>() {
            override fun areItemsTheSame(oldItem: HomeWidget, newItem: HomeWidget): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeWidget, newItem: HomeWidget): Boolean {
                return oldItem == newItem
            }
        }
        //ViewType 정의
        private const val TYPE_TITLE = 0
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is HomeWidget.Title -> TYPE_TITLE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> {
                val binding =
                    FragmentHomeTitleBinding.inflate(inflater, parent, false)
                return TitleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    /**
     *  연결된 값들을 바인딩
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeWidget.Title ->{
                (holder as TitleViewHolder).tvTitle.text = item.title
            }
        }
    }


    /**
     *  ViewHolder inner Class 정의
     *  해당 id을 불러 들어 변수 형태로 정의
     */
    inner class TitleViewHolder(binding: FragmentHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
    }
}