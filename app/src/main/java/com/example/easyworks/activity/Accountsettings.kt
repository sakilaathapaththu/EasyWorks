package com.example.easyworks.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.R
import com.example.easyworks.models.Users
import com.google.firebase.database.FirebaseDatabase


class Accountsettings : AppCompatActivity() {

    private lateinit var uid: TextView
    private lateinit var fname: TextView
    private lateinit var lname: TextView
    private lateinit var address1: TextView
    private lateinit var Tele:TextView
    private lateinit var username1:TextView
    private lateinit var updateprobutton:Button
    private lateinit var deleteprobutton:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accountsettings)

        initView()

        setValuesToViews()

        updateprobutton.setOnClickListener {
            openUpdateDialog(
                        intent.getStringExtra("uid").toString(),
                         intent.getStringExtra("firstname").toString()

            )
        }

        deleteprobutton.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("uid").toString()
            )
        }

    }

    private fun deleteRecord(
        uid:String
    ){
        val dbFef = FirebaseDatabase.getInstance().getReference("users").child(uid)
        val mTask = dbFef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)

        }.addOnFailureListener { error->
            Toast.makeText(this,"Deleting error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        uid = findViewById(R.id.uid)
        fname = findViewById(R.id.fname)
        lname = findViewById(R.id.lname)
        address1 = findViewById(R.id.address1)
        Tele = findViewById(R.id.Tele)
        username1 = findViewById(R.id.username1)
        updateprobutton = findViewById(R.id.updateprofile)
        deleteprobutton = findViewById(R.id.deleteprofile)

    }

    private fun setValuesToViews(){
        uid.text = intent.getStringExtra("uid")
        fname.text = intent.getStringExtra("firstname")
        lname.text = intent.getStringExtra("lastname")
        address1.text = intent.getStringExtra("address")
        Tele.text = intent.getStringExtra("phone")
        username1.text = intent.getStringExtra("email")


    }


    private fun openUpdateDialog(
        uid:String,
        firstname: String

    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog1,null)

        mDialog.setView(mDialogView)


        val upfirstname = mDialogView.findViewById<EditText>(R.id.updatefname)
        val uplastname = mDialogView.findViewById<EditText>(R.id.updatelname)
        val upaddress = mDialogView.findViewById<EditText>(R.id.updateaddress)
        val upphone = mDialogView.findViewById<EditText>(R.id.updatetele)
        val upemail = mDialogView.findViewById<EditText>(R.id.updateusername)

        val btnupdatedata =mDialogView.findViewById<Button>(R.id.updatepro)


        upfirstname.setText(intent.getStringExtra("firstname").toString())
        uplastname.setText(intent.getStringExtra("lastname").toString())
        upaddress.setText(intent.getStringExtra("address").toString())
        upphone.setText(intent.getStringExtra("phone").toString())
        upemail.setText(intent.getStringExtra("email").toString())

        mDialog.setTitle("Updating $firstname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnupdatedata.setOnClickListener {
            updateEmpData(

                upfirstname.text.toString(),
                uplastname.text.toString(),
                upaddress.text.toString(),
                upphone.text.toString(),
                upemail.text.toString(),
                uid
            )
            Toast.makeText(applicationContext,"emplyee data updated",Toast.LENGTH_LONG).show()

            fname.text = upfirstname.text.toString()
            lname.text = uplastname.text.toString()
            address1.text = upaddress.text.toString()
            Tele.text = upphone.text.toString()
            username1.text = upemail.text.toString()


            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(

        firstname: String,
        lastname:String,
        address:String,
        phone:String,
        email: String,
        uid:String
    ){
        val encodedEmail = email.replace(".", ".")
            .replace("#", ",")
            .replace("$", ",")
            .replace("[", ",")
            .replace("]", ",")
        val dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
        val empInfo = Users(firstname, lastname, address, phone, encodedEmail,null,uid)
        dbRef.setValue(empInfo)
    }


}