package com.example.gotamagica.Services.Repository.Remote.Viacep

import com.example.gotamagica.Services.Constants.ConstantsURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAddress private constructor(){

    companion object{

        private lateinit var instance : Retrofit

        private fun getInstance(): Retrofit{

            var httpClient = OkHttpClient.Builder()

            if(!Companion::instance.isInitialized){
                instance = Retrofit.Builder()
                    .baseUrl(ConstantsURL.CEP.VIACEP)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
           return instance
        }

        fun <s> createService(serviceClass : Class<s>): s{

            return getInstance().create(serviceClass)
        }


    }
}