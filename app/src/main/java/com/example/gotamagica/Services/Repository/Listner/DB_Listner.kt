package com.example.gotamagica.Services.Repository.Listner

import com.example.gotamagica.Services.Repository.Remote.db_API.API_Data_Db

interface DB_Listner {


    fun onSuccess(body: API_Data_Db, requestType : String){

    }

    fun onFailure(requestType : String){

    }

}