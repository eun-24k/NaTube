package com.example.natube.ui.myvideo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.natube.ModelPreferencesManager
import com.example.natube.VideoDetailActivity
import com.example.natube.databinding.FragmentMyVideosBinding
import com.example.natube.model.UnifiedItem
import com.example.natube.ui.home.HomeAdapter

class MyVideoFragment : Fragment() {

    private var _binding: FragmentMyVideosBinding? = null
    private val myVideoViewModel: MyVideoViewModel by viewModels()
    private val myVideoAdapter: MyVideoAdapter by lazy { MyVideoAdapter(myVideoViewModel) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val likedItems = ModelPreferencesManager.getAll<UnifiedItem>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("HappyMyVideo", "^^ from sharedpref $likedItems")
        val myVideoViewModel =
            ViewModelProvider(this)[MyVideoViewModel::class.java]

        _binding = FragmentMyVideosBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()

    }

    private fun initViewModel() {
        with(myVideoViewModel) {
            selectedItem.observe(viewLifecycleOwner) {

                when (it) {
                    null -> Unit
                    else -> {
                        val detailIntent = Intent(activity, VideoDetailActivity::class.java)
                        detailIntent.putExtra("selected item", it)
                        startActivity(detailIntent)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val likedItems = ModelPreferencesManager.getAll<UnifiedItem>()
        myVideoAdapter.submitList(likedItems)
    }
    private fun initView() {
        setViewModelValues()
        setMyVideoAdapter()
        setLikedVideos()
    }
    private fun setViewModelValues() {
        myVideoViewModel.getSelectedItem(null)
    }
    private fun setMyVideoAdapter() {

        binding.rvFragmentMyVideoFavourites.adapter = myVideoAdapter

        Log.d("happyMyVideoFragment_TagRV", "^^get SHaredPref ? ${likedItems.size}")
        myVideoAdapter.submitList(likedItems)

    }

    private fun setLikedVideos() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}