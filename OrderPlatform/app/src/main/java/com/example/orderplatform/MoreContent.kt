package com.example.orderplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MoreContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_content)

        val mIntent = intent
        val bundle = mIntent.extras

        val title: TextView = findViewById(R.id.more_title)
        val description: TextView = findViewById(R.id.more_description)
        val picture: ImageView = findViewById(R.id.more_picture)

        title.text = bundle!!.getString("title")
        description.text = getString(bundle.getInt("description"))
        picture.setImageResource(bundle.getInt("picture"))
    }
}