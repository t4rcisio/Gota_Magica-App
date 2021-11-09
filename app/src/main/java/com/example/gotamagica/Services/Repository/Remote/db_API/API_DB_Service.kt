package com.example.gotamagica.Services.Repository.Remote.db_API

import com.example.gotamagica.Services.Constants.ConstantsURL
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API_DB_Service {


    @POST(ConstantsURL.MONGODB.NEWUSER)
    fun saveNewUser(@Body body:NewUserModel) : Call<NewUserModel>

    @POST(ConstantsURL.MONGODB.LOGIN)
    fun login(@Body body: API_Data_Db): Call<API_Data_Db>

    @POST(ConstantsURL.MONGODB.EMAIL)
    fun mailVerify(@Body body : API_Data_Db): Call<API_Data_Db>

    @POST(ConstantsURL.MONGODB.CPF)
    fun cpfVerify(@Body body: API_Data_Db): Call<API_Data_Db>

}