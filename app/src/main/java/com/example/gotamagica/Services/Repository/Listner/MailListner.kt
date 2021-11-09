package com.example.gotamagica.Services.Repository.Listner

import com.example.gotamagica.Services.Repository.Remote.Mail.MailModel

interface MailListner {

    fun onSuccess(body : MailModel){

    }

    fun onFailure(){

    }


}