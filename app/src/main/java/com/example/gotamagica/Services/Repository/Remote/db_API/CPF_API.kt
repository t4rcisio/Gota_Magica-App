package com.example.gotamagica.Services.Repository.Remote.db_API

import com.example.gotamagica.Services.Repository.Listner.DB_Listner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CPF_API {

    private val remote = API_Retrofit.createService(API_DB_Service::class.java)

    fun checkCPF(body: API_Data_Db, listner : DB_Listner, requestType : String){

        var call : Call<API_Data_Db> = remote.cpfVerify(body)

        call.enqueue(object : Callback<API_Data_Db> {
            override fun onResponse(call: Call<API_Data_Db>, response: Response<API_Data_Db>) {
                var body = response.body()
                body?.let { listner.onSuccess(body, requestType) }
            }

            override fun onFailure(call: Call<API_Data_Db>, t: Throwable) {
                listner.onFailure(requestType)
            }


        })
    }
}