package com.example.project.simsappkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import util.Server
import java.util.*

@SuppressLint("Registered")
class LoginMain : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var email2: String
    private lateinit var password2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        email = findViewById(R.id.editText_email)
        password = findViewById(R.id.editText_password)
        login = findViewById(R.id.button_login)
        login.setOnClickListener {
            email2 = email.text.toString().trim { it <= ' ' }
            password2 = password.text.toString().trim { it <= ' ' }
            if (email2.isNotEmpty() && password2.isNotEmpty()) {
                handlekoneksilogin(email2, password2)
            } else {
                email.error = "Please Insert Email!"
                password.error = "Please insert Password!"
            }
        }
    }

    private fun doClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun handlekoneksilogin(email: String, password: String) {

        val jsonBody = JSONObject()
        try {
            jsonBody.put("email", email)
            jsonBody.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val strReq = object : JsonObjectRequest(
            Method.POST, Server.URL, jsonBody,
            Response.Listener { response ->
                try {
                    Log.i("response", response.toString())
                    val success = response.getString("success")

                    if (success == "1") {

                        val user = response.getJSONObject("users")
                        val name = user.getString("name")

                        Toast.makeText(
                            this@LoginMain,
                            "Hallo! $name Loginmu bener :)",
                            Toast.LENGTH_LONG
                        ).show()
                        doClick()
                    } else {
                        Toast.makeText(
                            this@LoginMain,
                            "email atau password salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    // JSON error
                    e.printStackTrace()

                    Toast.makeText(this@LoginMain, "Ora oleh data", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { error ->
                error.printStackTrace()
                val response = error.networkResponse
                if (response?.data != null) {
                    val errorString = String(response.data)
                    Log.i("log error", errorString)
                }
                Toast.makeText(this@LoginMain, "Error" + error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        strReq.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(strReq)
    }
}