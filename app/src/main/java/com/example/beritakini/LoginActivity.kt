package com.example.beritakini

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beritakini.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val registerLink = findViewById<TextView>(R.id.registerLink)

        continueBtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Mohon isi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}