package com.example.gotamagica.Services.Repository.Remote.Mail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface MailService {



    @GET()
    fun checkMail(@Url mail : String) : Call<MailModel>
}