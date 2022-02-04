package com.humanverse.humanverseapp.feature.home.ui.ui.ui.notifications

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.databinding.FragmentNotificationsBinding
import com.humanverse.humanverseapp.feature.auth.ui.LoginActivity
import com.humanverse.humanverseapp.util.FileUtil
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var filePath: Uri? = null
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;

        binding.logoutUser.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.profileImage.setOnClickListener {
            openGallery()
        }


    }

    fun openGallery() {
        val pickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            val mimetypes = arrayOf("image/*", "application/pdf/*")
            putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        }
        intentDocument.launch(pickIntent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Glide
                    .with(requireContext())
                    .load(data!!.data)
                    .centerCrop()
                    .into(binding.profileImage);

                filePath = data.data
//            var file = Uri.fromFile(File(getPathFromUri(requireContext(), filePath!!)))
//
                Toast.makeText(requireContext(), filePath.toString(), Toast.LENGTH_SHORT).show()
//            var stream:InputStream =FileInputStream(File(getPathFromUri(requireContext(), filePath!!)))
//            val ref = storageReference?.child(
//                "images/" + "user1"
//            )
//            if (filePath != null) {
//                ref!!.putStream(stream).addOnSuccessListener {
//                    Toast.makeText(requireContext(),"SUCCESS",Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener {
//                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
//                }
//            }
            }
        }
    private var intentDocument =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                var file: File? = FileUtil.from(requireContext(), result.data?.data!!)

                if (result.resultCode == Activity.RESULT_OK) {
                    Glide
                        .with(requireContext())
                        .load(file)
                        .centerCrop()
                        .into(binding.profileImage);
//            var file = Uri.fromFile(File(getPathFromUri(requireContext(), filePath!!)))
//
                    var stream: InputStream = FileInputStream(file)
                    //            var stream:InputStream =FileInputStream(File(getPathFromUri(requireContext(), filePath!!)))
                    val ref = storageReference?.child(
                        "images/" + "user1"
                    )

                    val uploadTask = ref?.putFile(file!!.toUri())

                    if (uploadTask != null) {
                        uploadTask.addOnFailureListener {
                            // Handle unsuccessful uploads
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }.addOnSuccessListener { taskSnapshot ->
                            Toast.makeText(requireContext(), "yay", Toast.LENGTH_SHORT).show()

                            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    }else{
                        Toast.makeText(requireContext(), "aaa", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
}