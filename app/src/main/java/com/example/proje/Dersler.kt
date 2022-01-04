package com.example.proje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class Dersler : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var user: String


    override fun onCreate(savedInstanceState: Bundle?) {
        var myLesson = ArrayList<String>()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dersler)
        user =intent.getStringExtra("Name").toString()

        drawerLayout = findViewById(R.id.drawerlayout)
        val navView = findViewById<NavigationView>(R.id.navigationview)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)



        fun lesson_rec(uname: String) {
            val url = "http://10.0.2.2:5000/kullaniciders"

            val json = JSONObject()
            json.put("_id", uname)
            val queue = Volley.newRequestQueue(this)
            val jsonapi: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, json,
                Response.Listener { response->

                    val values = response.getString("name").substring(0,response.getString("name").length-1)
                    val items: List<String> = values.split(",")
                    for (item in items)
                        myLesson.add(item)
                    val listview = findViewById<ListView>(R.id.listView)
                    val adapter = ArrayAdapter(this,R.layout.ders_item,R.id.textView,myLesson)
                    listview.adapter=adapter
                },
                Response.ErrorListener {}) {}
            queue.add(jsonapi)

        }
        lesson_rec(user)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dersler -> {
                val intent = Intent(this, Dersler::class.java)
                intent.putExtra("Name",user )
                startActivity(intent)
            }
            R.id.derskayit -> {
                val intent = Intent(this, derseklecikar::class.java)
                intent.putExtra("Name",user )
                startActivity(intent)
            }
            R.id.notlar -> {
                val intent = Intent(this, Notlar::class.java)
                intent.putExtra("Name",user )
                startActivity(intent)
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}