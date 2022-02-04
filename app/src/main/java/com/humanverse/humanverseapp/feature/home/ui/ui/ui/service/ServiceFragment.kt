package com.humanverse.humanverseapp.feature.home.ui.ui.ui.service

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.humanverse.humanverseapp.databinding.FragmentNotificationsBinding
import com.humanverse.humanverseapp.databinding.HistoryFragmentBinding
import com.humanverse.humanverseapp.feature.service.AddServiceActivity

class ServiceFragment : Fragment() {

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
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(requireContext(),AddServiceActivity::class.java))
        }
    }
}