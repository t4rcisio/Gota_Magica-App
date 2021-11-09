package com.example.gotamagica.Services.Repository.Remote.db_API

import com.example.gotamagica.Services.Constants.ConstantsURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API_Retrofit {

    companion object{

        private lateinit var instace : Retrofit

        private fun getInstce() : Retrofit{

            var httpClient = OkHttpClient.Builder()

            if(!Companion::instace.isInitialized){
                instace = Retrofit.Builder()
                    .baseUrl(ConstantsURL.MONGODB.BASEURL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instace
        }

        fun <s> createService(serviceClass : Class<s>): s{

            return getInstce().create(serviceClass)
        }


    }

}