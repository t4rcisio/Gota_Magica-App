package com.example.gotamagica.Services.Repository.Remote.Mail

import com.google.gson.annotations.SerializedName

class MailModel {

    @SerializedName("format_valid")
    var isValid : Boolean = false

    @SerializedName("email")
    var email : String = ""

    @SerializedName("success")
    var success : Boolean = false

    @SerializedName("did_you_mean")
    var did_you_mean : String = ""

    @SerializedName("mx_found")
    var isDomainValid : Boolean = false


}

/*
* {
  "email": "20193008761@aluno.cefetmg.br",
  "did_you_mean": "",
  "user": "20193008761",
  "domain": "aluno.cefetmg.br",
  "format_valid": true,
  "mx_found": true,
  "smtp_check": false,
  "catch_all": false,
  "role": false,
  "disposable": false,
  "free": false,
  "score": 0.64
}
*
*
*
*
*
* */