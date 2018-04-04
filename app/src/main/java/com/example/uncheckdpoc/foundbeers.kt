package com.example.uncheckdpoc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_foundbeers.*

private val VIEWMODEL_KEY = "beerscanner_viewmodel"

class foundbeers : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context, filePath: ArrayList<String>) : Intent {
            val intent = Intent(context, foundbeers::class.java)
            intent.putExtra(VIEWMODEL_KEY, filePath)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lines = intent.getStringArrayListExtra(VIEWMODEL_KEY);
        setContentView(R.layout.activity_foundbeers)
        setSupportActionBar(toolbar)

        val listView = this.findViewById(R.id.listofbeers);

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
