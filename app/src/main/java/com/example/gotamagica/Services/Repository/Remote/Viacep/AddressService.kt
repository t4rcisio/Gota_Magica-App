package com.example.gotamagica.Services.Repository.Remote.Viacep


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AddressService {


    @GET("{cep}/json/")
    fun dataCep(@Path("cep") cep: String) : Call<AddressModel>

}