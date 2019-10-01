package com.example.project.simsappkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lead_view_page.*
import okhttp3.*
import util.Server.getDataLeadRegister
import java.io.IOException

class PackageTracking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lead_view_page)

        expandable.layoutManager = LinearLayoutManager(this)

        fetchJson()
    }

    private fun fetchJson() {
        println("Attempting to fetch JSON")

        val request = Request.Builder().url(getDataLeadRegister).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeLead = gson.fromJson(body, HomeLead::class.java)

                runOnUiThread {
                    expandable.adapter = LeadAdapter(homeLead)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Gagal eksekusi")
            }
        })
    }
}

class HomeLead(val lead: List<LeadRegister>)

class LeadRegister(val lead_id: String, val opp_name: String)