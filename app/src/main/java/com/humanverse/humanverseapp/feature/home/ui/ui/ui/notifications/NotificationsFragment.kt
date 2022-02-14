package com.humanverse.humanverseapp.feature.home.ui.ui.ui.notifications

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.FragmentNotificationsBinding
import com.humanverse.humanverseapp.feature.auth.ui.LoginActivity
import com.humanverse.humanverseapp.feature.helpcenter.HelpCenterActivity
import com.humanverse.humanverseapp.util.Utils.showAlertDialogForTap
import java.io.File
import android.content.ClipData.Item

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.humanverse.humanverseapp.feature.payment.PaymentActivity
import com.humanverse.humanverseapp.util.*


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: StorageReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var mobile: String
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
        ref = storageReference?.child(
            "profile_pictures/" + auth.currentUser!!.email
        )!!
        binding.textView21.setOnClickListener {
            startActivity(Intent(requireContext(), HelpCenterActivity::class.java))
        }
        db.collection("trialstatus")
            .whereEqualTo("email", auth.currentUser!!.email)
            .get()
            .addOnSuccessListener {
                showConsent("You trial membership only "+it.documents.get(0).get("time").toString()+". Please renew to have access over 100's of services to provide and earn",requireActivity(),true){
                    hideDialog()
                }
                binding.textView23.text = it.documents.get(0).get("time").toString()
            }
            .addOnFailureListener { e ->

            }

        binding.logoutUser.setOnClickListener {
            showRatingDialog(requireActivity()){
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=humanverse")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=humanverse")
                        )
                    )
                }
            }
        }
        binding.shareuser1.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "Welcome to Humanverse, feel free show share and let others know. \n Visit: https://sites.google.com/view/humanverse/home"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Share with."))
            } catch (e: java.lang.Exception) {
                //e.toString();
            }
        }
        binding.textView12.isEnabled = false
        binding.progressBar3.visibility = View.VISIBLE
        ref?.downloadUrl?.addOnSuccessListener {
            try {
                Glide
                    .with(requireContext())
                    .load(it)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.plus_holder)
                    .into(binding.profileImage)
                binding.progressBar3.visibility = View.GONE
            } catch (e: Exception) {

            }
        }.addOnFailureListener {
            binding.progressBar3.visibility = View.GONE
        }
        db.collection("user").document(auth.currentUser!!.email.toString())
            .get()
            .addOnSuccessListener { documents ->
                mobile = documents.get("mobile").toString()
                binding.textView12.setText(
                    if (documents.get("name").toString() == "null") {
                        "Add Name"
                    } else {
                        documents.get("name").toString()
                    }
                )
            }
            .addOnFailureListener { exception ->
            }

        binding.shareuser.setOnClickListener {

            showAlertDialogForTap(requireActivity(),
                "Signing out?",
                "Are you sure you want to sign out from the app?",
                {
                    auth.signOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                },
                {

                })

        }

        binding.profileImage.setOnClickListener {
            openGallery()
        }
        binding.textView12.setOnFocusChangeListener { view, b ->
            if (b) {
                view.isEnabled = true
                binding.button6.visibility = View.VISIBLE
            }
        }
        binding.button8.setOnClickListener {
            val city = hashMapOf(
                "name" to binding.textView12.text.toString(),
                "mobile" to mobile,
            )
            db.collection("user")
                .document(auth.currentUser!!.email!!.toString())
                .set(city)
                .addOnSuccessListener {
                    binding.textView12.isEnabled = false
                    binding.button8.visibility = View.GONE
                    binding.button6.visibility = View.VISIBLE
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
        }
        binding.button6.setOnClickListener {

            binding.button6.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.white))
            binding.button8.visibility = View.VISIBLE
            binding.textView12.isEnabled = true
            binding.textView12.selectAll()
            binding.textView12.requestFocus()
            val inputMethodManager =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

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

    private var intentDocument =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            binding.progressBar3.visibility = View.VISIBLE

            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                var file: File? = FileUtil.from(requireContext(), result.data?.data!!)

                if (result.resultCode == Activity.RESULT_OK) {
                    Glide
                        .with(requireContext())
                        .load(file)
                        .centerCrop()
                        .into(binding.profileImage);

                    val uploadTask = ref?.putFile(file!!.toUri())

                    if (uploadTask != null) {
                        uploadTask.addOnFailureListener {
                            // Handle unsuccessful uploads
                            binding.progressBar3.visibility = View.GONE

                        }.addOnSuccessListener { taskSnapshot ->
                            binding.progressBar3.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(requireContext(), "aaa", Toast.LENGTH_SHORT)
                        binding.progressBar3.visibility = View.GONE

                    }
                }
            } else {
                binding.progressBar3.visibility = View.GONE
            }
        }
}