package com.example.easyworks.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyworks.R
import com.example.easyworks.adapters.EmpAdapter
import com.example.easyworks.models.Users
import com.google.firebase.database.*

class FetchingActivity: AppCompatActivity() {

    private lateinit var empRecyclerview : RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<Users>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fetchingview)

        empRecyclerview = findViewById(R.id.rvEmp)
        empRecyclerview.layoutManager = LinearLayoutManager(this)
        empRecyclerview.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<Users>()

        getEmployeeData()

    }
    private fun getEmployeeData(){
        empRecyclerview.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("users")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(Users::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,Accountsettings::class.java)

                            //put extras
                            //intent.putExtra("uid",empList[position].uid)
                            intent.putExtra("uid",empList[position].uid)
                            intent.putExtra("firstname",empList[position].firstname)
                            intent.putExtra("lastname",empList[position].lastname)
                            intent.putExtra("address",empList[position].address)
                            intent.putExtra("phone",empList[position].phone)
                            intent.putExtra("email",empList[position].email)
                            startActivity(intent)

                        }

                    })

                    empRecyclerview.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}