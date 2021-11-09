package com.example.gotamagica.Services.Repository.Remote.Viacep

import com.google.gson.annotations.SerializedName

class AddressModel {


    @SerializedName("cep")
    var cep : String = ""

    @SerializedName("logradouro")
    var logradouro : String = ""

    @SerializedName("complemento")
    var complemento : String = ""

    @SerializedName("bairro")
    var bairro : String = ""

    @SerializedName("localidade")
    var localidade : String = ""

    @SerializedName("uf")
    var uf : String = ""
}