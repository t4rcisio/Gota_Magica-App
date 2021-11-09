package com.example.gotamagica.Services.Repository.Remote.Viacep

import com.example.gotamagica.Services.Repository.Listner.AddressListner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressAPI {


    private val remote = RetrofitAddress.createService(AddressService::class.java)



    fun getCEP(cep: String, listner: AddressListner){

        var call : Call<AddressModel> = remote.dataCep(cep)

        call.enqueue( object : Callback<AddressModel>{
            override fun onResponse(call: Call<AddressModel>, response: Response<AddressModel>) {
                val body =  response.body()
                body?.let{ listner.onSucess(body) }
            }

            override fun onFailure(call: Call<AddressModel>, t: Throwable) {
                listner.onFailure(t.message.toString())
            }

        })

    }

}