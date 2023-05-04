package com.example.easyworks.activity

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.R
import com.example.easyworks.models.Users
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TestSingin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private val emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrationpage)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val cusEmpfirstname :EditText = findViewById(R.id.editTextTextPersonName)
        val cusEmplastname :EditText = findViewById(R.id.editTextTextPersonName2)
        val cusEmpaddress :EditText = findViewById(R.id.editTextTextPostalAddress)
        val cusEmpphone :EditText = findViewById(R.id.editTextPhone)
        val cusEmpemail : EditText =findViewById(R.id.editTextTextEmailAddress)
        val cusEmppassword : EditText = findViewById(R.id.editTextTextPassword2)
        val cusEmprepassword :EditText =findViewById(R.id.editTextTextPassword3)

        val cusEmpcheckbox : CheckBox = findViewById(R.id.checkBox2)
        val regbutton : MaterialButton = findViewById(R.id.regibutton1)

        regbutton.setOnClickListener {
            val firstname = cusEmpfirstname.text.toString()
            val lastname = cusEmplastname.text.toString()
            val address = cusEmpaddress.text.toString()
            val phone = cusEmpphone.text.toString()
            val email = cusEmpemail.text.toString()
            val password = cusEmppassword.text.toString()
            val repassword = cusEmprepassword.text.toString()

            val checkbox = cusEmpcheckbox.text.toString()

            if (firstname.isEmpty()||lastname.isEmpty()||address.isEmpty()||phone.isEmpty()||email.isEmpty()||password.isEmpty()||repassword.isEmpty()||checkbox.isEmpty()){
                if(firstname.isEmpty()){
                    cusEmpfirstname.error = "Please Enter First Name"
                }
                if(lastname.isEmpty()){
                    cusEmplastname.error = "Please Enter Last Name"
                }
                if(address.isEmpty()){
                    cusEmpaddress.error = "Please Enter Address"
                }
                if(phone.isEmpty()){
                    cusEmpphone.error = "Please Enter Phone Number"
                }
                if(email.isEmpty()){
                    cusEmpemail.error = "Please Enter Email"
                }
                if(password.isEmpty()){
                    cusEmppassword.error = "Please Enter Password"
                }
                if(repassword.isEmpty()){
                    cusEmprepassword.error = "Please ReEnter Password"
                }

                if(checkbox.isEmpty()){
                    cusEmpcheckbox.error = "Please Tick"
                }
                Toast.makeText( this,"Enter Valid Details", Toast.LENGTH_SHORT).show()

            }else if (!email.matches(emailpattern.toRegex())){
                cusEmpemail.error ="enter valid email address"
                Toast.makeText( this,"Enter Valid Email Address", Toast.LENGTH_SHORT).show()

            }else if (phone.length != 10){
                cusEmpphone.error ="enter valid phone number"
                Toast.makeText( this,"Enter Valid phone number", Toast.LENGTH_SHORT).show()
            }else if(password.length<6){
                cusEmpemail.error ="enter password more than 6 characters"
                Toast.makeText( this,"Enter password more than 6 characters", Toast.LENGTH_SHORT).show()
            }else if(password != repassword){
                cusEmpemail.error ="password not matched , try again"
                Toast.makeText( this,"password not matched , try again", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                        val users: Users = Users(firstname,lastname,address,phone,email,checkbox,auth.currentUser!!.uid)

                        databaseRef.setValue(users).addOnCompleteListener {
                            if (it.isSuccessful){
                                val intents = Intent( this, Login::class.java)
                                startActivity(intents)
                            }else{
                                Toast.makeText( this,"something went wrong , try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText( this,"something went wrong , try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }
}