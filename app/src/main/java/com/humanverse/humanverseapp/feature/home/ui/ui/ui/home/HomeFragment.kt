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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.FragmentHomeBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapter
import com.humanverse.humanverseapp.feature.service.AllServiceActivity
import com.humanverse.humanverseapp.feature.service.ServiceActivity
import com.humanverse.humanverseapp.model.HistoryServiceModel
import com.humanverse.humanverseapp.model.ModelDashboardItem
import com.humanverse.humanverseapp.model.ServiceModel
import com.humanverse.humanverseapp.util.SERVICE_TPYE
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.showLoader


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: DashboardItemAdapter
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    var dashboardItems : MutableList<ModelDashboardItem> = mutableListOf()
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
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;
        adapter = DashboardItemAdapter(requireContext())
        initSlider()

        showLoader("Loading, please wait...", requireActivity())
        binding.button3.setOnClickListener {
            startActivity(Intent(requireContext(),AllServiceActivity::class.java))
        }
        db.collection("category")
            .get()
            .addOnSuccessListener {
                hideConsent()
                for(document in it.documents){
                    dashboardItems.add(
                        ModelDashboardItem(
                            document.get("title").toString(),
                            document.get("image").toString(),
                            document.get("name").toString()
                        )
                    )
                    dashboardItems.sortBy {
                        it.title
                    }
                }
                adapter.submitListData(dashboardItems)
                val layoutManager = GridLayoutManager(
                    activity, 3, GridLayoutManager.VERTICAL, false
                )
                binding.recyclerViewDashboarditem.layoutManager = layoutManager
                binding.recyclerViewDashboarditem.adapter = adapter
                adapter.itemActionListener = {
                    val intent = Intent(requireContext(), ServiceActivity::class.java)
                    intent.putExtra(SERVICE_TPYE,it.type)
                    intent.putExtra("NAME",it.title)
                    startActivity(intent)
                }

                hideConsent()
            }
            .addOnFailureListener { e ->
                hideConsent()
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()

            }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}