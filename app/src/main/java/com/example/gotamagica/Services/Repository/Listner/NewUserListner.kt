package com.example.gotamagica.Services.Repository.Listner

import com.example.gotamagica.Services.Repository.Remote.db_API.NewUserModel

interface NewUserListner {


    fun onSuccess(body: NewUserModel){

    }

    fun onFailure(){

    }
}