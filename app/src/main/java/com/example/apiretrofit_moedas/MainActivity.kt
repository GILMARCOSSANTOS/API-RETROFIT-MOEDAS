package com.example.apiretrofit_moedas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Funcctions: */

        declareFunction()
        declareVariables()
        hideActionBar()
        centerTitleActionBar()
    }

    private fun declareVariables() {

    }

    private fun declareFunction() {

    }

    private fun hideActionBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun centerTitleActionBar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.center_title_actionbar)
    }
}