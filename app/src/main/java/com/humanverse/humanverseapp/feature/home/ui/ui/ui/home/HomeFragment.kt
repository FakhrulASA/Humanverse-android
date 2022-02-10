package com.humanverse.humanverseapp.feature.home.ui.ui.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aemerse.slider.model.CarouselItem
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.FragmentHomeBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapter
import com.humanverse.humanverseapp.feature.service.ServiceActivity
import com.humanverse.humanverseapp.util.SERVICE_TPYE


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DashboardItemAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    val vm: HomeViewModel by viewModels()
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
        val imageList = ArrayList<SlideModel>() // Create image list

        listSlider = mutableListOf<CarouselItem>()
        // Image URL with
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/humanverse-57f4d.appspot.com/o/Web%20Banner_300_0902.jpg?alt=media&token=0455b618-49c6-415a-a2f6-9cfc4c451589"))

        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val uri = Uri.parse("https://21c5b6k6p9-8wjnh9ec3bl0y3j.hop.clickbank.net/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)            }
        })
    }

    private fun initList() {
        vm.setValue()
        vm.listItem.observe(this) { dashboarditem ->
            adapter = DashboardItemAdapter(requireContext())
            Log.d("DataListFound", dashboarditem.toString())
            adapter.submitListData(dashboarditem)
            val layoutManager = GridLayoutManager(
                activity, 3, GridLayoutManager.VERTICAL, false
            )
            binding.recyclerViewDashboarditem.layoutManager = layoutManager
            binding.recyclerViewDashboarditem.adapter = adapter
            adapter.itemActionListener = {
                val intent = Intent(requireContext(), ServiceActivity::class.java)
                intent.putExtra(SERVICE_TPYE,it.type)
                startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}