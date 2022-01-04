package com.example.proje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val username = findViewById<EditText>(R.id.ad)
        val sifre = findViewById<EditText>(R.id.sifre)
        button.setOnClickListener {
            sends(username.text.toString(),sifre.text.toString())

        }
        button2.setOnClickListener {
            sends2(username.text.toString(),sifre.text.toString())

        }
    }
    fun sends(uname: String, sifre: String) {
        val url = "http://10.0.2.2:5000/login"

        val json = JSONObject()
        json.put("_id", uname)
        json.put("sifre", sifre)

        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response->

               if(response.getString("_id").toString() == uname){
                   val intent = Intent(this, Dersler::class.java)
                   intent.putExtra("Name", response.getString("_id").toString())
                   startActivity(intent)

               }
            },
            Response.ErrorListener {
                Toast.makeText( this,"kullancı adı veya şifre yanlış", Toast.LENGTH_SHORT).show()
            }) {}
        queue.add(jsonapi)


    }
    fun sends2(uname: String, sifre: String) {
        val url = "http://10.0.2.2:5000/login2"

        val json = JSONObject()
        json.put("_id", uname)
        json.put("sifre", sifre)

        val queue = Volley.newRequestQueue(this)
        val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, json,
            Response.Listener { response->

                if(response.getString("_id").toString() == uname){
                    val intent = Intent(this, notgiris::class.java)
                    intent.putExtra("Name", response.getString("_id").toString())
                    startActivity(intent)

                }
            },
            Response.ErrorListener {
                Toast.makeText( this,"kullancı adı veya şifre yanlış", Toast.LENGTH_SHORT).show()
            }) {}
        queue.add(jsonapi)


    }
}