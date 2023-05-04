package com.example.easyworks.activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easyworks.models.EmployeeModel
import com.example.easyworks.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration : AppCompatActivity() {

    private lateinit var cusEmpfirstname: EditText
    private lateinit var cusEmplastname: EditText
    private lateinit var cusEmpaddress: EditText
    private lateinit var cusEmpphone: EditText
    private lateinit var cusEmpemail: EditText
    private lateinit var cusEmppassword: EditText
    private lateinit var cusEmprepassword: EditText

    private lateinit var cusEmpcheckbox: CheckBox
    private lateinit var regbutton: MaterialButton

    private lateinit var dbrefEmp : DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrationpage)

        cusEmpfirstname =findViewById(R.id.editTextTextPersonName)
        cusEmplastname =findViewById(R.id.editTextTextPersonName2)
        cusEmpaddress =findViewById(R.id.editTextTextPostalAddress)
        cusEmpphone =findViewById(R.id.editTextPhone)
        cusEmpemail =findViewById(R.id.editTextTextEmailAddress)
        cusEmppassword=findViewById(R.id.editTextTextPassword2)
        cusEmprepassword =findViewById(R.id.editTextTextPassword3)

        cusEmpcheckbox = findViewById(R.id.checkBox2)
        regbutton = findViewById(R.id.regibutton1)


        dbrefEmp = FirebaseDatabase.getInstance().getReference("Employees")

        regbutton.setOnClickListener{
            saveEmployeeData()
        }


    }

    private fun saveEmployeeData(){
        //getting values
        val empfirstname = cusEmpfirstname.text.toString()
        val emplastname = cusEmplastname.text.toString()
        val empaddress = cusEmpaddress.text.toString()
        val empphone = cusEmpphone.text.toString()
        val empemail = cusEmpemail.text.toString()
        val emppassword = cusEmppassword.text.toString()
        val emprepassword = cusEmprepassword.text.toString()

        val empcheckbox = cusEmpcheckbox.text.toString()

        if(empfirstname.isEmpty()){
            cusEmpfirstname.error = "Please Enter First Name"
        }
        if(emplastname.isEmpty()){
            cusEmplastname.error = "Please Enter Last Name"
        }
        if(empaddress.isEmpty()){
            cusEmpaddress.error = "Please Enter Address"
        }
        if(empphone.isEmpty()){
            cusEmpphone.error = "Please Enter Phone Number"
        }
        if(empemail.isEmpty()){
            cusEmpemail.error = "Please Enter Email"
        }
        if(emppassword.isEmpty()){
            cusEmppassword.error = "Please Enter Password"
        }
        if(emprepassword.isEmpty()){
            cusEmprepassword.error = "Please ReEnter Password"
        }

        if(empcheckbox.isEmpty()){
            cusEmpcheckbox.error = "Please Tick"
        }

        val empId = dbrefEmp.push().key!!

        var employee = EmployeeModel(empId,empfirstname,emplastname,empaddress,empphone,empemail,emppassword,emprepassword, empcheckbox)
        dbrefEmp.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText( this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                cusEmpfirstname.text.clear()
                cusEmplastname.text.clear()
                cusEmpaddress.text.clear()
                cusEmpphone.text.clear()
                cusEmpemail.text.clear()
                cusEmprepassword.text.clear()
                cusEmppassword.text.clear()
                //cusEmpcheckbox.text.clear()
                val regiButton = findViewById<MaterialButton>(R.id.regibutton1)
                regiButton.setOnClickListener {
                    val regi = Intent(this, Login::class.java)
                    startActivity(regi)
                }



            }.addOnFailureListener { err ->
                Toast.makeText( this,"Error ${err.message}", Toast.LENGTH_LONG).show()
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
//
//private fun Any.clear() {
//    TODO("Not yet implemented")
//}
