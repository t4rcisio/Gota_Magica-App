package com.example.gotamagica.Services.Repository.Listner

import com.example.gotamagica.Services.Repository.Remote.Viacep.AddressModel

interface AddressListner {


    fun onSucess(body: AddressModel){

    }

    fun onFailure(errorMessage : String){

    }

}