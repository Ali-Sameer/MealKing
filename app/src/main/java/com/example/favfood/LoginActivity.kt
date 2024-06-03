package com.example.favfood


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.favfood.Model.UserModel
import com.example.favfood.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database



@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private  var userName:String? = null
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var databse: DatabaseReference
    private lateinit var GoogleSignInClient :GoogleSignInClient


private val binding: ActivityLoginBinding by lazy{
    ActivityLoginBinding.inflate(layoutInflater)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        //Intilization of firebse auth
        auth = Firebase.auth

        //Intilization of firebse databse

        databse = Firebase.database.reference

        //Intilization of google

        GoogleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions)

        //login with email and password
        binding.loginbutton.setOnClickListener{

            //get data from text field


            email = binding.emailAddress.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank()||password.isBlank()){
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }
            else{
                createUser()
                Toast.makeText(this, "Login-Successfully", Toast.LENGTH_SHORT).show()
            }


        }

        binding.noaccountbtn.setOnClickListener{
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

//google sign in

        binding.googlebutton.setOnClickListener{
            val signIntent = GoogleSignInClient.signInIntent

            launcher.launch(signIntent)

        }


    }

    //launcher google

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful) {
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(this, "Sign In Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){
                val user = auth.currentUser
             updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        saveUserData()
                        val user = auth.currentUser
                        updateUi(user)
                    }else{
                        Toast.makeText(this, "Sign-in Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                
            }
        }
    }

    private fun saveUserData() {
        //get data from text field

        email = binding.emailAddress.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        // save data in to database
        databse.child("user").child(userId).setValue(user)


    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!= null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}