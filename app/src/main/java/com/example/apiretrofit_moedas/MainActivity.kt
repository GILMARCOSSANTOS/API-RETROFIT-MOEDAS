package com.example.apiretrofit_moedas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.telecom.Call
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import com.example.api.EndPoint
import com.example.util.NetWorkUtils
import com.google.gson.JsonObject
import okhttp3.Callback
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    /* Global Scope Variables */
    private lateinit var spFrom: Spinner
    private lateinit var spTo: Spinner
    private lateinit var btConvert: Button
    private lateinit var tvResult: TextView
    private lateinit var etValueFrom: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Functions: */
        declareVariables()
        hideActionBar()
        centerTitleActionBar()
        getCurrencies()
        buttonConvert()
    }

    private fun declareVariables() {
        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        btConvert = findViewById(R.id.btConvert)
        tvResult = findViewById(R.id.tvResult)
        etValueFrom = findViewById(R.id.etValueFrom)
    }

    private fun hideActionBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun centerTitleActionBar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.center_title_actionbar)
    }

    fun getCurrencies() {

        val retrofitClient = NetWorkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endPoint = retrofitClient.create(EndPoint::class.java)

        endPoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(
                call: retrofit2.Call<JsonObject>,
                response: retrofit2.Response<JsonObject>
            ) {

                val data = mutableListOf<String>()
                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }
                println(data.count())

                val posBRL = data.indexOf("brl")
                val posUSD = data.indexOf("usd")

                val adapter =
                    ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)
                spFrom.adapter = adapter
                spTo.adapter = adapter
                spFrom.setSelection(posBRL)
                spTo.setSelection(posUSD)
            }

            override fun onFailure(call: retrofit2.Call<JsonObject>, t: Throwable) {
                println("Falha no Sistema!")
            }
        })
    }

    private fun convertMoney() {
        val retrofitClient = NetWorkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endPoint = retrofitClient.create(EndPoint::class.java)

        endPoint.getCurrencyRate(spFrom.selectedItem.toString(), spTo.selectedItem.toString())
            .enqueue(object : retrofit2.Callback<JsonObject> {
                override fun onResponse(
                    call: retrofit2.Call<JsonObject>,
                    response: retrofit2.Response<JsonObject>
                ) {
                    var data =
                        response.body()?.entrySet()?.find { it.key == spTo.selectedItem.toString() }
                    var rate: Double = data?.value.toString().toDouble()
                    val conversion = etValueFrom.text.toString().toDouble() * rate
                    tvResult.setText(conversion.toString())
                }

                override fun onFailure(call: retrofit2.Call<JsonObject>, t: Throwable) {
                    println("Falha no Sistema.02")
                }
            })
    }

    private fun buttonConvert() {
        btConvert.setOnClickListener {

            if (btConvert.isClickable) {
                convertMoney()
            }
            if (etValueFrom.text.toString().toDouble() == 0.0 ) {
                tvResult.text.toString() == "Prencha o valor da moeda"

            }
        }
    }
}
