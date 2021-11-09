package com.example.gotamagica.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotamagica.Services.Constants.ConstantsURL
import com.example.gotamagica.Services.Repository.Listner.AddressListner
import com.example.gotamagica.Services.Repository.Listner.DB_Listner
import com.example.gotamagica.Services.Repository.Listner.MailListner
import com.example.gotamagica.Services.Repository.Listner.NewUserListner
import com.example.gotamagica.Services.Repository.Remote.Mail.MailAPI
import com.example.gotamagica.Services.Repository.Remote.Mail.MailModel
import com.example.gotamagica.Services.Repository.Remote.Viacep.AddressAPI
import com.example.gotamagica.Services.Repository.Remote.Viacep.AddressModel
import com.example.gotamagica.Services.Repository.Remote.db_API.*
import com.example.gotamagica.Services.localValidation.cpfAuthenticator
import java.util.*


class NewUserViewModel : ViewModel() {

    private var mAddressAPI : AddressAPI = AddressAPI()

    private var mNewUserAPI : NewUserAPI = NewUserAPI()

    private var mMailAPI_DB : EmailAPI = EmailAPI()

    private var mCPF_API : CPF_API = CPF_API()

    private var mCpfAuthenticator: cpfAuthenticator = cpfAuthenticator()

    private var mMailAPI : MailAPI = MailAPI()

    private var pCity : MutableLiveData<String>  = MutableLiveData()
    var mCity : LiveData<String> = pCity

    private var pNeighborhood : MutableLiveData<String>  = MutableLiveData()
    var mNeighborhood : LiveData<String> = pNeighborhood

    private var pState : MutableLiveData<String>  = MutableLiveData()
    var mState : LiveData<String> = pState

    private var pStreet : MutableLiveData<String>  = MutableLiveData()
    var mStreet : LiveData<String> = pStreet

    private var mMailData : MutableLiveData<Boolean>  = MutableLiveData()
    var mMailResponse : LiveData<Boolean> = mMailData

    private var mMailDB : MutableLiveData<Boolean>  = MutableLiveData()
    var mMailResponseDB : LiveData<Boolean> = mMailDB

    private var pCepData : MutableLiveData<Boolean> = MutableLiveData()
    var mCepResponse : LiveData<Boolean> = pCepData

    private var pBirthday : MutableLiveData<String> = MutableLiveData()
    var mbirthdayResponse : LiveData<String> = pBirthday

    private var pPhone : MutableLiveData<String> = MutableLiveData()
    var mPhoneResponse : LiveData<String> = pPhone

    private var pPassword1 : MutableLiveData<Boolean> = MutableLiveData()
    var mPassword1 : LiveData<Boolean> = pPassword1

    private var pPassword2 : MutableLiveData<Boolean> = MutableLiveData()
    var mPassword2 : LiveData<Boolean> = pPassword2

    private var pPasswordVisibility : MutableLiveData<Boolean> = MutableLiveData()
    var mPasswordVisibility : LiveData<Boolean> = pPasswordVisibility

    private var pCpfAuthenticator : MutableLiveData<Boolean> = MutableLiveData()
    var mCpfValidator : LiveData<Boolean> = pCpfAuthenticator

    private var pCpfResponseDB : MutableLiveData<Boolean> = MutableLiveData()
    var mCpfResponseDB : LiveData<Boolean> = pCpfResponseDB

    private var pBirthdayValidate : MutableLiveData<Boolean> = MutableLiveData()
    var mBirthdayValidate : LiveData<Boolean> = pBirthdayValidate

    private var pNameValidate : MutableLiveData<Boolean> = MutableLiveData()
    var mNameValidate : LiveData<Boolean> = pNameValidate

    private var pPhoneValidate : MutableLiveData<Boolean> = MutableLiveData()
    var mPhonelidate : LiveData<Boolean> = pPhoneValidate

    private var pUserSave : MutableLiveData<Boolean> = MutableLiveData()
    var mUserSaved : LiveData<Boolean> = pUserSave

    private var pLoad : MutableLiveData<Boolean> = MutableLiveData()
    var mLoad : LiveData<Boolean> = pLoad

    private var pErrorImputData : MutableLiveData<Boolean> = MutableLiveData()
    var mErrorImputData : LiveData<Boolean> = pErrorImputData

    private var bCPF = false
    private var bEmail = false
    private var bPassoword = false




    fun validateCep(cep : String, listner : AddressListner){

        if(cep.length == 8){
            mAddressAPI.getCEP(cep, listner)
        }

    }

    fun validateMail(mail: String, listner: MailListner){
        mMailAPI.checkMail(mail, listner)
    }

    fun validateMailResponse(body: MailModel, listner: DB_Listner){

        if(body.email.any() && body.did_you_mean == ""){
            var mBody = API_Data_Db()
            mBody.email = body.email
            mMailAPI_DB.mailCheck(mBody, listner, ConstantsURL.MONGODB.EMAIL)
        }else{
            mMailData.value = false
        }
    }
    fun validadteMailResponse(){

        mMailData.value = false

    }

    private fun validateMailResponse(body: API_Data_Db){
        mMailDB.value = body.sucess
        bEmail = !mMailDB.value!!
    }

    private fun validateCPFResponse(body: API_Data_Db){
        pCpfResponseDB.value = body.sucess
        bCPF = !pCpfResponseDB.value!!
    }

    fun db_responseRedirect(body: API_Data_Db, requestType: String){

        when(requestType){

            ConstantsURL.MONGODB.EMAIL -> validateMailResponse(body)
            ConstantsURL.MONGODB.CPF -> validateCPFResponse(body)

        }

    }

    fun checkCPF(cpf: String,listner: DB_Listner): Boolean{

        if(mCpfAuthenticator.checkCPF(cpf)){
            var body = API_Data_Db()
            body.cpf = cpf
            mCPF_API.checkCPF(body,listner,ConstantsURL.MONGODB.CPF)
        }else{
            pCpfAuthenticator.value = false
        }

        return pCpfAuthenticator.value!!
    }


    fun readBody(body : AddressModel){

        if(body.bairro.any())
            pNeighborhood.value  = body.bairro

        if(body.localidade.any()) {
            pCity.value = body.localidade
            pCepData.value = true
        }else{
            pCepData.value = false
        }

        if(body.logradouro.any())
            pStreet.value = body.logradouro

        if(body.uf.any())
            pState.value = body.uf

    }

    fun birthdayFormat(birthday: String) : Boolean{

       var size = birthday.length

        if(size>3 && birthday[2]!='/' && birthday[3]!='/' && birthday[1]!='/') {
            pBirthday.value = birthday.replaceRange(2,2, '/'.toString())
        }
        if(size>6 && birthday[5]!='/' && birthday[4]!='/' && birthday[6]!='/') {
            pBirthday.value = birthday.replaceRange(5,5, '/'.toString())
        }

        if(size == 10){
            return try {

                var day = "${birthday.subSequence(0,2)}".toInt()
                var month = "${birthday.subSequence(3,5)}".toInt()
                var year = "${birthday.subSequence(6,10)}".toInt()

                pBirthdayValidate.value =  (day in 1..31) && (month in 1..12) && (year > 1900 && year < Calendar.getInstance().get(Calendar.YEAR))
                true
            }catch (e: Exception){
                pBirthdayValidate.value = false
                false
            }
        }
        return false
    }

    fun phoneFormat(phone: String): Boolean{

        var size = phone.length


        if(size > 0 && phone[0] != '('){
            pPhone.value = phone.replaceRange(0,0,"(")
        }
        if(size > 3 && phone[3] != ')' && phone[2] != ')'){
            pPhone.value = phone.replaceRange(3,3,") ")
        }
        if(size > 10 && phone[10] != '-' && phone[9] != '-'){
            pPhone.value = phone.replaceRange(10,10,"-")
        }

        return if(size < 15){
            pPhoneValidate.value = false
            pPhoneValidate.value!!
        }else{
            pPhoneValidate.value = true
            pPhoneValidate.value!!
        }

    }

    fun passwordVisibility(){

        pPasswordVisibility.value = pPasswordVisibility.value != true

    }

    private fun passwordFormat(password : String, varState : MutableLiveData<Boolean>): Boolean{
        varState.value =  (password.any() && password.length >= 8)
        return varState.value!!
    }

    fun checkPassword1(password1: String, password2: String){
        if(password2.any()){
            checkPassword2(password1,password2)
        }
        passwordFormat(password1,pPassword1)
    }

    fun checkPassword2(password1: String,password2: String){
        if(passwordFormat(password1, pPassword1)){
            if(passwordFormat(password2, pPassword2 )){
                pPassword2.value = password1 == password2

            }else{
                pPassword2.value = false
            }
        }else{
            pPassword2.value =  false
        }
        bPassoword = pPassword2.value!!
    }

    fun savedUser(body : NewUserModel){

        pUserSave.value = body.sucess

    }



    fun checkName(name: String): Boolean{

        for(c in name){
            var temp = c.digitToIntOrNull()
            if(temp != null){
                pNameValidate.value = false
                return false
            }
        }
        pNameValidate.value = true
        return true
    }




    fun saveUser(name : String,
                 birthday: String,
                 cpf: String,
                 email: String,
                 phone: String,
                 zipcode: String,
                 uf : String,
                 city : String,
                 neighborhood: String,
                 address: String,
                 password : String,
                 listner: NewUserListner
                 ) {

        if (checkName(name) && birthdayFormat(birthday) && bCPF
            && phoneFormat(phone) && uf.any() && address.any() && bPassoword && bEmail)
            {

        pLoad.value = true

            var userData = NewUserModel()

            userData.name = name
            userData.birthday = birthday
            userData.cpf = cpf
            userData.email = email
            userData.phone = phone
            userData.zipcode = zipcode
            userData.uf = uf
            userData.city = city
            userData.neighborhood = neighborhood
            userData.address = address
            userData.password = password

            mNewUserAPI.postNewUser(userData, listner)

        }else{
            pErrorImputData.value = true
        }
    }
}