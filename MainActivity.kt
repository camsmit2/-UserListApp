package com.example.userlistapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userlistapp.data.User
import com.example.userlistapp.ui.UserAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFetch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserAdapter

    private val client = AsyncHttpClient()
    private val endpoint = "https://jsonplaceholder.typicode.com/users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        btnFetch = findViewById(R.id.btnFetch)
        progressBar = findViewById(R.id.progressBar)

        adapter = UserAdapter(mutableListOf()) { user ->
            Toast.makeText(this, "You selected ${user.name}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnFetch.setOnClickListener { fetchUsers() }
    }

    private fun fetchUsers() {
        progressBar.visibility = View.VISIBLE
        client.get(endpoint, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONArray?
            ) {
                val users = mutableListOf<User>()
                if (response != null) {
                    for (i in 0 until response.length()) {
                        val obj: JSONObject = response.getJSONObject(i)
                        val name = obj.optString("name")
                        val email = obj.optString("email")
                        val phone = obj.optString("phone")
                        users.add(User(name, email, phone))
                    }
                }
                adapter.setItems(users)
                progressBar.visibility = View.GONE
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@MainActivity,
                    "Failed to fetch data.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
