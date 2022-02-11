package com.humanverse.humanverseapp.feature.home.ui.ui.ui.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.databinding.HistoryFragmentBinding
import com.humanverse.humanverseapp.feature.service.AddServiceActivity
import com.humanverse.humanverseapp.feature.service.ServiceActivity
import com.humanverse.humanverseapp.feature.service.ServiceItemAdapter
import com.humanverse.humanverseapp.model.ServiceModel
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.showLoader

class ServiceFragment : Fragment() {
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    var dataList: MutableList<ServiceModel> = mutableListOf()
    private lateinit var adapter: ServiceItemAdapter

    companion object {
        fun newInstance() = ServiceFragment()
    }

    private lateinit var viewModel: ServiceViewModel
    private var _binding: HistoryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        showLoader("loading! \n Please wait...", requireActivity())
        storageReference = storage!!.reference
        db.collection("/services/")
            .whereEqualTo("email", auth.currentUser?.email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() != 0) {
                    for (document in documents) {
                        dataList.add(
                            ServiceModel(
                                img = document.get("banner").toString(),
                                title = document.get("serviceName").toString(),
                                des = document.get("description").toString(),
                                price = document.get("price").toString().toInt(),
                                id = document.id
                            )
                        )
                    }
                } else {
                    binding.layouttextnone.visibility = View.VISIBLE
                }
                adapter = ServiceItemAdapter(requireContext())
                adapter.submitListData(dataList)
                val layoutManager = GridLayoutManager(
                    requireContext(), 1, GridLayoutManager.VERTICAL, false
                )
                binding.recService.layoutManager = layoutManager
                binding.recService.adapter = adapter
                hideConsent()
                adapter.itemActionListener = {
                    startActivity(Intent(requireContext(), ServiceActivity::class.java))
                }
            }
            .addOnFailureListener { exception ->
                hideConsent()
                Log.w("DATA FOUND FRB", "Error getting documents: ", exception)
            }
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(requireContext(), AddServiceActivity::class.java))
        }
    }
}