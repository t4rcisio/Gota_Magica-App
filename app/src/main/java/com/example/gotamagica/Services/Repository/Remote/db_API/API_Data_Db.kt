package com.example.gotamagica.Services.Repository.Remote.db_API

import com.google.gson.annotations.SerializedName

class API_Data_Db {

    @SerializedName("name")
    var name : String = ""

    @SerializedName("email")
    var email : String = ""

    @SerializedName("password")
    var password : String = ""

    @SerializedName("cpf")
    var cpf : String = ""

    @SerializedName("id")
    var id : String = ""

    @SerializedName("sucess")
    var sucess : Boolean = false



}