package com.example.easyworks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.R
import com.example.easyworks.databinding.ProfilepageBinding

import com.example.easyworks.databinding.UpdatepasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpdateLoginpassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: UpdatepasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdatepasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.updateemail1.setOnClickListener {
            val user = auth.currentUser
            val email = binding.updateloginemail.text.toString()
            val password = binding.updateloginpassword.text.toString()

            if (checkEmailField()){
                user?.updateEmail(email)?.addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"email update Successfully !",Toast.LENGTH_SHORT).show()

                    }else{
                        Log.e("error :",it.exception.toString())
                    }
                }

            }


        }
        binding.updateep1.setOnClickListener {
            val user = auth.currentUser

            val password = binding.updateloginpassword.text.toString()

            if (checkPasswordField()) {
                user?.updatePassword(password)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "password update Successfully !", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("error :", it.exception.toString())
                    }
                }
            }
        }

        binding.gobackbutton.setOnClickListener {
            val intend = Intent(this, Login::class.java)
            startActivity(intend)
        }

    }

    private fun checkEmailField(): Boolean {
        val email = binding.updateloginemail.text.toString()
        if (binding.updateloginemail.text.toString()==""){
            Toast.makeText(this,"this is required field !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this," check email format !",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun checkPasswordField(): Boolean {

        if (binding.updateloginpassword.text.toString() == "") {
            Toast.makeText(this, "this is required field !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.updateloginpassword.length() <= 6) {
            Toast.makeText(
                this,
                " password should at least 7 characters long !",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}