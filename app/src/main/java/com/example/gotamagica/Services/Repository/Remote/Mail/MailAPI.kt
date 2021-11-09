package com.example.gotamagica.Services.Repository.Remote.Mail

import com.example.gotamagica.Services.Constants.ConstantsURL
import com.example.gotamagica.Services.Repository.Listner.MailListner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MailAPI {

    private val remote = RetrofitMail.creteService(MailService::class.java)


    fun checkMail(mail: String, listner: MailListner){

        var url  = ConstantsURL.EMAIL.KEY + mail + ConstantsURL.EMAIL.TYPE
        var call: Call<MailModel> = remote.checkMail(url)

        call.enqueue(object : Callback<MailModel>{
            override fun onResponse(call: Call<MailModel>, response: Response<MailModel>) {
                val body = response.body()
                body?.let {  listner.onSuccess(body) }

                /*println("\nMail "+body?.email)
                println("Is valid: "+body?.isValid)
                println("success: "+body?.success)
                println("did you mean: "+body?.did_you_mean+"\n")*/
            }

            override fun onFailure(call: Call<MailModel>, t: Throwable) {
                listner.onFailure()
            }

        })

    }



}