package com.example.beritakini

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        continueBtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                            // Redirect to another activity
                            startActivity(Intent(this, LoginActivity::class.java))
                        } else {
                            Toast.makeText(this, "Pendaftaran Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}