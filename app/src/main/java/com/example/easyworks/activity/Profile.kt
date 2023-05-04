package com.example.easyworks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.easyworks.R
import com.example.easyworks.databinding.ProfilepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ProfilepageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfilepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.logoutbutton.setOnClickListener{
            auth.signOut()

            val intent = Intent(this,Login::class.java)
            startActivity(intent)

            finish()
        }

        binding.deletebutton.setOnClickListener{
            val user = Firebase.auth.currentUser
            user?.delete()?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Account Successfully Deleted !",Toast.LENGTH_SHORT).show()

                    val intent = Intent(this,Login::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.e("error: ", it.exception.toString())
                }
            }
        }

        val accountpro = findViewById<AppCompatButton>(R.id.accountsetting)
        accountpro.setOnClickListener {
            val accprofile = Intent(this, FetchingActivity::class.java)
            startActivity(accprofile)
        }

        val accountnotifi = findViewById<AppCompatButton>(R.id.notificationbutton)
        accountnotifi.setOnClickListener {
            val notifiprofile = Intent(this, Notification::class.java)
            startActivity(notifiprofile)
        }
        val updatepass = findViewById<AppCompatButton>(R.id.updatebutton)
        updatepass.setOnClickListener {
            val intent = Intent(this, UpdateLoginpassword::class.java)
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mainmenu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.dashboard -> {
                dashboard1()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}