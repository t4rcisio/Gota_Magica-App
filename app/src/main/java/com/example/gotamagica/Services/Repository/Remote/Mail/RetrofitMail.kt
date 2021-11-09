package com.example.gotamagica.Services.Repository.Remote.Mail

import com.example.gotamagica.Services.Constants.ConstantsURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitMail private constructor() {

    companion object{

        private lateinit var instance : Retrofit

        private fun getInstance() : Retrofit{

            var httpClient = OkHttpClient.Builder()

            if(!Companion::instance.isInitialized){

                instance = Retrofit.Builder()
                    .baseUrl(ConstantsURL.EMAIL.MAILBASE)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return instance
        }

       fun <t> creteService(service: Class<t>): t {
           return getInstance().create(service)
       }

    }



}