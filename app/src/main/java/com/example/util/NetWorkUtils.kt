package com.example.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertPath

class NetWorkUtils {
    // Criar um Singleton
    companion object {
        fun getRetrofitInstance(path: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}