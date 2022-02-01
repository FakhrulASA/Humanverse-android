package com.humanverse.humanverseapp.feature.home.ui.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aemerse.slider.model.CarouselItem
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.FragmentHomeBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapter
import com.humanverse.humanverseapp.feature.service.ServiceActivity
import com.humanverse.humanverseapp.model.ModelDashboardItem


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DashboardItemAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    val vm : HomeViewModel by viewModels()
    lateinit var listSlider: MutableList<CarouselItem>
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initSlider()


    }

    private fun initSlider() {
        binding.carousel.registerLifecycle(lifecycle)
        listSlider = mutableListOf<CarouselItem>()
        // Image URL with caption
        listSlider.add(
            CarouselItem(
                imageDrawable = R.drawable.homebanner_1
            )
        )
        // Just image URL
        listSlider.add(
            CarouselItem(
                imageDrawable = R.drawable.banner2
            )
        )
        binding.carousel.setData(listSlider)
    }

    private fun initList() {
        vm.setValue()
        vm.listItem.observe(this){
            adapter = DashboardItemAdapter(requireContext())
            Log.d("DataListFound",it.toString())
            adapter.submitListData(it)
            val layoutManager = GridLayoutManager(
                activity, 3, GridLayoutManager.VERTICAL, false
            )
            binding.recyclerViewDashboarditem.layoutManager = layoutManager
            binding.recyclerViewDashboarditem.adapter = adapter
            adapter.itemActionListener = {
                startActivity(Intent(requireContext(),ServiceActivity::class.java))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}