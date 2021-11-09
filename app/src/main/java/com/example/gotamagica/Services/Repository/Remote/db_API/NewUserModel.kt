package com.example.gotamagica.Services.Repository.Remote.db_API

import com.google.gson.annotations.SerializedName

class NewUserModel {

    @SerializedName("name")
    var name : String = ""

    @SerializedName("birthday")
    var birthday : String = ""

    @SerializedName("cpf")
    var cpf : String = ""

    @SerializedName("email")
    var email : String = ""

    @SerializedName("phone")
    var phone : String = ""

    @SerializedName("zipcode")
    var zipcode : String = ""

    @SerializedName("uf")
    var uf : String = ""

    @SerializedName("city")
    var city : String = ""

    @SerializedName("neighborhood")
    var neighborhood : String = ""

    @SerializedName("address")
    var address : String = ""

    @SerializedName("password")
    var password : String = ""

    @SerializedName("sucess")
    var sucess : Boolean = false



}