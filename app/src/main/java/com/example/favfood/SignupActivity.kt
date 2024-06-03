package com.example.favfood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.favfood.Model.UserModel

import com.example.favfood.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username: String
    private lateinit var    auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSingInClient : GoogleSignInClient

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

       val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()


        //initilize firebase auth
       auth = Firebase.auth

        //initilize firebase database
        database = Firebase.database.reference

        //initilize firebase database

        googleSingInClient = GoogleSignIn.getClient(this,googleSignInOptions)





        binding.accounthai.setOnClickListener{

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }


        binding.createAccountButton.setOnClickListener{
        username = binding.userName.text.toString()
            email = binding.emailAdress.text.toString().trim()
            password = binding.Password.text.toString().trim()
            
            if (email.isBlank()||password.isBlank()||username.isBlank()){
                Toast.makeText(this, "Please Fill all the details", Toast.LENGTH_SHORT).show()

            }else
            {
                createAccount(email,password)
            }

        }


        binding.googleButton.setOnClickListener{
            val signIntent = googleSingInClient.signInIntent

            launcher.launch(signIntent)

        }


    }
//launcher for google sign in
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        Toast.makeText(this, "Sign In Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            else{
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
            if (task.isSuccessful){
                Toast.makeText(this, "Account Created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Account Creation Filed", Toast.LENGTH_SHORT).show()
                Log.d("Account","creationAccount : Failure",task.exception)
            }
        }

    }

    private fun saveUserData() {

        //retrive data from input filed
        username = binding.userName.text.toString()
        password = binding.Password.text.toString().trim()
        email = binding.emailAdress.text.toString().trim()

        val user = UserModel(username,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save data to fire base databse
        database.child("user").child(userId).setValue(user)
    }
}