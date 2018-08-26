package com.example.venkatnutalapati.nytimesnews.screens.detailedNews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.venkatnutalapati.nytimesnews.R


import kotlinx.android.synthetic.main.activity_detailed_web_view.*
import kotlinx.android.synthetic.main.detailed_web_view_layout.*

class DetailedWebViewActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_detailed_web_view)

      val message = intent.getStringExtra("news")

      webview.loadUrl(message)

      floating_button.setOnClickListener(object : View.OnClickListener {
         override fun onClick(v: View?) {
            val sendIntent: Intent = Intent().apply {
               this.putExtra(Intent.EXTRA_TEXT, message)
               type = "text/plain"
               action = Intent.ACTION_SEND
               startActivity(Intent.createChooser(this, "Sharing option"))
            }
         }
      })
   }
}


