package com.example.gotamagica.Services.Constants

class ConstantsURL private constructor() {

    object  CEP{

        val VIACEP = "https://viacep.com.br/ws/"
    }


    object EMAIL{

        const val MAILBASE = "http://apilayer.net/api/"
        const val KEY = "check?access_key=ef5fdc27a78c2da55fc3b7de5306e638&email="
        const val TYPE = "&smtp=1&format=1"
    }

    object SEC_PREFERENCES{
        const val SEC_UID = "UID"
        const val SEC_MAIL = "MAIL"
        const val SEC_PSW = "PSW"
    }

    object MONGODB{
        const val BASEURL = "https://gota-magica-api-jet.vercel.app/api/"
        const val NEWUSER = "saveUser"
        const val EMAIL = "email"
        const val CPF = "cpf"
        const val LOGIN = "login"
    }

}