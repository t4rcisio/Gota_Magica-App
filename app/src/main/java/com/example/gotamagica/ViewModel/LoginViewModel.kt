package com.example.gotamagica.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotamagica.Services.Constants.ConstantsURL
import com.example.gotamagica.Services.Repository.Listner.DB_Listner
import com.example.gotamagica.Services.Repository.Local.SecurityPreferences
import com.example.gotamagica.Services.Repository.Remote.db_API.LoginAPI
import com.example.gotamagica.Services.Repository.Remote.db_API.API_Data_Db
import com.example.gotamagica.Services.Repository.Remote.db_API.EmailAPI

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var mMailAPI = EmailAPI()
    private var mLoginAPI = LoginAPI()
    private var mSecurityPreferences = SecurityPreferences(application)

    private var mMailData : MutableLiveData<Boolean> = MutableLiveData()
    var mailListner : LiveData<Boolean> = mMailData

    private var mPasswordData : MutableLiveData<Boolean> = MutableLiveData()
    var passwordListner : LiveData<Boolean> = mPasswordData

    private var mLoginSuccess : MutableLiveData<Boolean> = MutableLiveData()
    var LoginSuccess : LiveData<Boolean> = mLoginSuccess

    private var mAutoLogin : MutableLiveData<Boolean> = MutableLiveData()
    var AutoLogin : LiveData<Boolean> = mAutoLogin

    private var mIdUser : MutableLiveData<String> = MutableLiveData()
    var IdUser : LiveData<String> = mIdUser






    fun mainCheck(email : String, listner: DB_Listner){

        if(email.any()) {
            var body = API_Data_Db()
            body.email = email
            mMailAPI.mailCheck(body, listner, ConstantsURL.MONGODB.EMAIL)
        }

    }

    fun checkPassword(password: String){

        mPasswordData.value = (password.any() && password.length >= 8)
    }

    private fun mailResponse(body: API_Data_Db){

        mMailData.value =  body.sucess

    }

    fun callAPI_Type(body: API_Data_Db, requestType: String, mSecurityPreferences : SecurityPreferences){

        when(requestType){
            ConstantsURL.MONGODB.EMAIL -> mailResponse(body)
            ConstantsURL.MONGODB.LOGIN -> loginResponse(body, mSecurityPreferences)
        }


    }

    fun verifyLogged(){
        var id = mSecurityPreferences.get(ConstantsURL.SEC_PREFERENCES.SEC_UID)
        var mail = mSecurityPreferences.get(ConstantsURL.SEC_PREFERENCES.SEC_MAIL)
        var psw = mSecurityPreferences.get(ConstantsURL.SEC_PREFERENCES.SEC_PSW)

        mAutoLogin.value = (id.any() && mail.any() && psw.any())

    }



    private fun loginResponse(body: API_Data_Db, mSecurityPreferences: SecurityPreferences){
        if(body.id.any() && body.email.any() && body.password.any()){

            mSecurityPreferences.store(ConstantsURL.SEC_PREFERENCES.SEC_UID, body.id)
            mSecurityPreferences.store(ConstantsURL.SEC_PREFERENCES.SEC_MAIL, body.email)
            mSecurityPreferences.store(ConstantsURL.SEC_PREFERENCES.SEC_PSW, body.password)
            mIdUser.value = body.id
        }
        mLoginSuccess.value = (body.sucess)

    }

    fun autoLogin(email: String, password: String, listner: DB_Listner){
        var body = API_Data_Db()
        body.email = email
        body.password = password
        mLoginAPI.login(body, listner, ConstantsURL.MONGODB.LOGIN)
    }


    fun login(email: String, password: String, listner: DB_Listner){
        if(email.any() && password.any()) {
            if (mPasswordData.value!! && mMailData.value!!) {
                var body = API_Data_Db()
                body.email = email
                body.password = password
                mLoginAPI.login(body, listner, ConstantsURL.MONGODB.LOGIN)
            } else {
                mLoginSuccess.value = false
            }
        }else{
            mLoginSuccess.value = false
        }
    }



}