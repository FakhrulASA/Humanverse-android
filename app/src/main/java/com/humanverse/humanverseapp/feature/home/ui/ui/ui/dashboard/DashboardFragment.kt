package com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.databinding.FragmentDashboardBinding
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.DashboardItemAdapter
import com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter.HistoryItemAdapter
import com.humanverse.humanverseapp.feature.service.OrderDetailsActivity
import com.humanverse.humanverseapp.feature.service.OrderProgressActivity
import com.humanverse.humanverseapp.model.HistoryServiceModel
import com.humanverse.humanverseapp.model.ModelDashboardItem
import com.humanverse.humanverseapp.util.hideConsent
import com.humanverse.humanverseapp.util.showConsent
import com.humanverse.humanverseapp.util.showLoader

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference
    var storage: FirebaseStorage? = null
    lateinit var adapter :  HistoryItemAdapter
    var storageReference: StorageReference? = null
    // This property is only valid between onCreateView and
    var documentList : MutableList<HistoryServiceModel> = mutableListOf()
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference


        adapter = HistoryItemAdapter(requireContext())

        adapter.itemActionListener = {
            var intent = Intent(requireContext(), OrderProgressActivity::class.java)
            intent.putExtra("NAME",it.title)
            intent.putExtra("CONTACT",it.contact)
            intent.putExtra("ID",it.serviceId)
            intent.putExtra("STATUS",it.status)
            intent.putExtra("PRICE",it.price)
            intent.putExtra("DATE",it.datetime)
            intent.putExtra("ORDERID",it.orderId)
            startActivity(intent)
        }
        showLoader("Loading, please wait!",requireActivity())
        db.collection("order")
            .whereEqualTo("customer", auth.currentUser!!.email.toString())
            .get()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),it.documents.size.toString(),Toast.LENGTH_SHORT).show()
                for(document in it.documents){
                    documentList.add(
                        HistoryServiceModel(
                            document.get("serviceName").toString(),
                            document.get("price").toString().toDouble(),
                            document.get("status").toString().toInt(),
                            document.get("date").toString()+", "+document.get("time").toString(),
                            document.get("serviceName").toString(),
                            document.get("provider").toString(),
                            document.id
                        )
                    )
                    documentList.sortBy {
                        it.status
                    }
                }
                adapter.submitListData(documentList)
                binding.dashboardRecycler.adapter  = adapter
                hideConsent()
            }
            .addOnFailureListener { e ->
                hideConsent()
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()

            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}