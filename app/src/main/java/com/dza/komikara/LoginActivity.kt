package com.dza.komikara

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dza.komikara.admin.AdminHome
import com.dza.komikara.databinding.ActivityLoginBinding
import com.dza.komikara.network.ApiClient
import com.dza.komikara.user.UserMain
import com.dza.retrofit.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private val apiService = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        // Cek apakah pengguna sudah login
        if (prefManager.isLoggedIn()) {
            if (prefManager.isAdmin()) {
                startActivity(Intent(this, AdminHome::class.java))
            } else {
                startActivity(Intent(this, UserMain::class.java))
            }
            finish()
            return
        }

        with(binding) {
            btnLogin.setOnClickListener {
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else if (isValidAdmin()) {
                    prefManager.setAdmin(true)
                    prefManager.setLoggedIn(true)
                    Toast.makeText(this@LoginActivity, "Login berhasil sebagai Admin", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, AdminHome::class.java))
                    finish()
                } else {
                    // Buat objek user untuk login
                    val user = Users(username = "", email = email, password = password)
                    apiService.loginUserByEmail(email, password).enqueue(object : Callback<List<Users>> {
                        override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                            if (response.isSuccessful) {
                                val usersList = response.body()
                                usersList?.let {
                                    // Mencari pengguna yang cocok
                                    val loggedInUser  = it.find { user -> user.email == email && user.password == password }
                                    if (loggedInUser  != null) {
                                        prefManager.setLoggedIn(true)
                                        prefManager.setAdmin(false)
                                        prefManager.saveEmail(loggedInUser .email)
                                        prefManager.saveUsername(loggedInUser .username)
                                        Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@LoginActivity, UserMain::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(this@LoginActivity, "Login gagal: Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    }
                                } ?: run {
                                    Toast.makeText(this@LoginActivity, "Login gagal: Respons kosong", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, "Login gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun isValidAdmin(): Boolean {
        val email = "admin@gmail.com" // Email yang valid
        val password = "admin1234" // Password yang valid
        val inputEmail = binding.txtEmail.text.toString()
        val inputPassword = binding.txtPassword.text.toString()
        return email == inputEmail && password == inputPassword
    }
}
