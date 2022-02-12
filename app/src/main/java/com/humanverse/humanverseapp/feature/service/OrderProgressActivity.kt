package com.humanverse.humanverseapp.feature.service

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ActivityOrderProgressBinding
import android.content.Intent
import com.humanverse.humanverseapp.feature.home.ui.ui.HomeActivity
import com.humanverse.humanverseapp.util.showConsent


class OrderProgressActivity : AppCompatActivity() {

    var name : String =""
    var contact: String = ""
    var id: String = ""
    var status: Int = -1
    var price: String = ""
    var date: String = ""
    var orderID: String = ""
    private lateinit var binding : ActivityOrderProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.extras!!.get("NAME").toString()
        contact = intent.extras!!.get("CONTACT").toString()
        id = intent.extras!!.get("ID").toString()
        status = intent.extras!!.get("STATUS").toString().toInt()
        price = intent.extras!!.get("PRICE").toString()
        date = intent.extras!!.get("DATE").toString()
        orderID = intent.extras!!.get("ORDERID").toString()

        binding.textView24.text = name
        binding.textView25.text = "Estimated completion date: "+date
        binding.textView252.text = "ORDER ID: "+orderID
        if(status==1){
            binding.textView29.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.button9.text="Contact provider"
        }
        if(status==2){
            binding.textView291.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.button9.text="Contact provider"

        }
        if(status==3){
            binding.textView291.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.textView1291.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.button9.text="Contact provider"
        }
        if(status==4){
            binding.textView291.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.textView1291.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.textView12911.setTextColor(Color.parseColor("#FFAB37BF"))
            binding.button9.text="Rate service"
            binding.textView27.text = "Your service is completed"
        }
        binding.button9.setOnClickListener {
            if(status==1||status==2||status==3){
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, contact)
                email.putExtra(Intent.EXTRA_SUBJECT, "Ref service: $orderID")
                //need this to prompts email client only
                //need this to prompts email client only
                email.type = "message/rfc822"

                startActivity(Intent.createChooser(email, "Choose an Email client :"))
            }else{
                showConsent("Rating service will be available soon", this, true){
                    startActivity(Intent(this, HomeActivity::class.java))
                }
            }
        }
    }
}