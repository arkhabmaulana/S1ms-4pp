package com.example.project.simsappkotlin

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

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private lateinit var save: Button
    private lateinit var input_trim: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input_text)
        save = findViewById(R.id.button_save)

        save.setOnClickListener {
            input_trim = input.text.toString().trim { it <= ' ' }
            if (input_trim.isNotEmpty()) {
                saveData(input_trim)
            } else {
                input.error = "Inputan ini wajib diisi!"
            }
        }

    }

    private fun saveData(input: String) {

        val jsonBody = JSONObject()
        try {
            jsonBody.put("input", input)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val strReq = object : JsonObjectRequest(
            Method.POST, Server.saveData, jsonBody,
            Response.Listener { response ->
                try {
                    Toast.makeText(
                        this@MainActivity,
                        "Date berhasil masuk. Yeay!",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: JSONException) {
                    // JSON error
                    e.printStackTrace()

                    Toast.makeText(this@MainActivity, "Ora oleh data", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener { error ->
                error.printStackTrace()
                val response = error.networkResponse
                if (response?.data != null) {
                    val errorString = String(response.data)
                    Log.i("log error", errorString)
                }
                Toast.makeText(this@MainActivity, "Error" + error.message, Toast.LENGTH_SHORT)
                    .show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["input"] = input
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

    private fun doClick() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
