package com.example.morningfirebasedbapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber: EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtIdNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("SAVING")
        progressDialog.setMessage("please wait....")
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            if (name.isEmpty()){
                edtName.setError("Please enter this fields")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please enter this fields")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()){
                edtIdNumber.setError("please enter this field")
                edtIdNumber.requestFocus()
            }else{
                //proceed to save
                //prepare the user to be saved
                var user = User(name, email, idNumber, id)
                //Create a reference in the firebase darabase
                var ref = FirebaseDatabase.getInstance().
                getReference().child("Users/"+id)
                //show the progress to start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    //
                    task ->
                    progressDialog.dismiss()
                    if(task.isSuccessful){
                        Toast.makeText(this,"User saved successfully"
                        ,Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"user saving failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            
        }
        btnView.setOnClickListener {

        }

    }
}