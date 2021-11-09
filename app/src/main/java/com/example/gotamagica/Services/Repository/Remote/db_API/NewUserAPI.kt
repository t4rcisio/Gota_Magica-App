package com.example.gotamagica.Services.Repository.Remote.db_API

import com.example.gotamagica.Services.Repository.Listner.NewUserListner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUserAPI {

    private val remote = API_Retrofit.createService(API_DB_Service::class.java)

    fun postNewUser(body: NewUserModel, listner : NewUserListner){

        var call : Call<NewUserModel> = remote.saveNewUser(body)

        call.enqueue(object : Callback<NewUserModel>{
            override fun onResponse(call: Call<NewUserModel>, response: Response<NewUserModel>) {
                var body = response.body()
                body?.let{listner.onSuccess(body)}
            }

            override fun onFailure(call: Call<NewUserModel>, t: Throwable) {
                println("\nError to save user\n")
                listner.onFailure()
            }

        })
    }



}