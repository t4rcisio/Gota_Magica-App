package com.example.gotamagica.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gotamagica.R
import com.example.gotamagica.Services.Repository.Listner.AddressListner
import com.example.gotamagica.Services.Repository.Listner.DB_Listner
import com.example.gotamagica.Services.Repository.Listner.MailListner
import com.example.gotamagica.Services.Repository.Listner.NewUserListner
import com.example.gotamagica.Services.Repository.Remote.Mail.MailModel
import com.example.gotamagica.Services.Repository.Remote.Viacep.AddressModel
import com.example.gotamagica.Services.Repository.Remote.db_API.API_Data_Db
import com.example.gotamagica.Services.Repository.Remote.db_API.NewUserModel
import com.example.gotamagica.ViewModel.NewUserViewModel

class NewUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mNewUserViewModel: NewUserViewModel
    private lateinit var mAddressListner: AddressListner
    private lateinit var mMailListner: MailListner
    private lateinit var mNewUserListner: NewUserListner
    private lateinit var mDB_Listner: DB_Listner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        mNewUserViewModel = ViewModelProvider(this).get(NewUserViewModel::class.java)

        if(supportActionBar != null)
            supportActionBar!!.hide()



        mNewUserViewModel.mCity.observe(this,{

            var city = findViewById<EditText>(R.id.form_guest_city)
            city.setText(it)
            city.isEnabled = false

        })

        mNewUserViewModel.mNeighborhood.observe(this,{

            var neighborhood = findViewById<EditText>(R.id.form_guest_neighborhood)
            neighborhood.setText(it)
            neighborhood.isEnabled = false
        })

        mNewUserViewModel.mState.observe(this,{

            var data = mNewUserViewModel.mState.value.toString()
            var state = findViewById<EditText>(R.id.form_guest_State)
            state.setText(it)
            state.isEnabled = false

        })

        mNewUserViewModel.mStreet.observe(this,{

            var address = findViewById<EditText>(R.id.form_guest_address)
            address.setText(it)

        })

        mNewUserViewModel.mMailResponse.observe(this,{
            var mail = findViewById<EditText>(R.id.form_guest_mail)
            if(it){
                mail.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                mail.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mCepResponse.observe(this,{
            var cep = findViewById<EditText>(R.id.form_guest_zipcode)
            if(it){
                cep.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                cep.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mbirthdayResponse.observe(this,{
            var birthday = findViewById<EditText>(R.id.form_guest_age)
            birthday.setText(it)
            birthday.setSelection(birthday.length())
        })

        mNewUserViewModel.mPhoneResponse.observe(this,{
            var phone = findViewById<EditText>(R.id.form_guest_phone)
            phone.setText(it)
            phone.setSelection(phone.length())
        })


        mAddressListner = object : AddressListner {

            override fun onSucess(body: AddressModel) {
                super.onSucess(body)
               mNewUserViewModel.readBody(body)

            }

            override fun onFailure(errorMessage: String) {
                super.onFailure(errorMessage)
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER - ${errorMessage}", Toast.LENGTH_LONG).show()
            }
        }
        mDB_Listner = object : DB_Listner {
            override fun onSuccess(body: API_Data_Db, requestType: String) {
                super.onSuccess(body, requestType)
                mNewUserViewModel.db_responseRedirect(body,requestType)
            }

            override fun onFailure(requestType: String) {
                Toast.makeText(applicationContext, "ERROR TO CONNECT TO SERVER - ($requestType)", Toast.LENGTH_LONG).show()
            }
        }


        mMailListner = object : MailListner {

            override fun onSuccess(body: MailModel) {
                super.onSuccess(body)
                mNewUserViewModel.validateMailResponse(body, mDB_Listner)
            }

            override fun onFailure() {
                super.onFailure()
                mNewUserViewModel.validadteMailResponse()
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER", Toast.LENGTH_LONG).show()
            }
        }

        mNewUserListner = object : NewUserListner {
            override fun onSuccess(body: NewUserModel) {
                super.onSuccess(body)
                mNewUserViewModel.savedUser(body)
            }

            override fun onFailure() {
                super.onFailure()
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER", Toast.LENGTH_LONG).show()
            }
        }

        mNewUserViewModel.mMailResponseDB.observe(this,{
            var mail = findViewById<EditText>(R.id.form_guest_mail)
            var text = findViewById<TextView>(R.id.email_already_exist)
            if(it){
                text.setVisibility(View.VISIBLE)
                mail.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }else{
                text.setVisibility(View.GONE)
                mail.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }
        })

        mNewUserViewModel.mCpfResponseDB.observe(this, {
            var cpf = findViewById<EditText>(R.id.form_guest_cpf)
            var text = findViewById<TextView>(R.id.cpf_already_exist)

            if(it){
                text.setVisibility(View.VISIBLE)
                cpf.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }else{
                text.setVisibility(View.GONE)
                cpf.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }
        })

        mNewUserViewModel.mUserSaved.observe(this,{

            if(it){
                backToMain()
            }else{
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER", Toast.LENGTH_LONG).show()
            }

        })


        mNewUserViewModel.mErrorImputData.observe(this,{
            if(it){
                unLoadAnimation()
            }
        })




        mNewUserViewModel.mPassword1.observe(this,{
            var password1 = findViewById<EditText>(R.id.form_guest_password1)
            if(it){
                password1.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                password1.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })


        mNewUserViewModel.mPassword2.observe(this,{
            var password1 = findViewById<EditText>(R.id.form_guest_password2)
            var passText = findViewById<TextView>(R.id.text_Password2)
            if(it){
                password1.background.setTint(ContextCompat.getColor(this,R.color.background_input))
                passText.text = "Repita sua senha:"
                passText.setTextColor(ContextCompat.getColor(this,R.color.text))
            }else{
                passText.text = "As senhas não são iguais"
                passText.setTextColor(ContextCompat.getColor(this,R.color.errorTex))
                password1.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mPasswordVisibility.observe(this,{
            var password1 = findViewById<EditText>(R.id.form_guest_password2)
            var password2 = findViewById<EditText>(R.id.form_guest_password1)
            if(!it){
                password1.inputType = 129
                password2.inputType = 129
            }else{
                password1.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                password2.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            password1.setSelection(password1.length())
            password2.setSelection(password2.length())
        })

        mNewUserViewModel.mCpfValidator.observe(this,{
            var cpf = findViewById<EditText>(R.id.form_guest_cpf)
            if(it){
                cpf.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                cpf.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mBirthdayValidate.observe(this,{
            var birthday = findViewById<EditText>(R.id.form_guest_age)
            if(it){
                birthday.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                birthday.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mNameValidate.observe(this,{
            var name = findViewById<EditText>(R.id.form_guest_name)
            if(it){
                name.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }else{
                name.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }
        })

        mNewUserViewModel.mLoad.observe(this,{
            if(it){
                loadAnimation()
            }else{
                error2save()
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER", Toast.LENGTH_LONG).show()
            }
        })

        listnerCEP()
        listnerCPF()
        listnerName()
        birthdayObserver()
        phoneObserver()
        mailObserver()
        password1Observer()
        password2Observer()

        var saveButton = findViewById<AppCompatButton>(R.id.saveButton)
        saveButton.setOnClickListener(this)
        var passwordVisibility = findViewById<ImageView>(R.id.pswVisibility)
        passwordVisibility.setOnClickListener(this)


    }

    override fun onClick(v: View) {

        var id = v.id

        when(id){
            R.id.saveButton -> saveNewUser()
            R.id.pswVisibility -> changePasswordVisibility()
        }

    }

    fun backToMain(){

        onDestroyView()

    }

    private fun onDestroyView() {
        finish()
    }

    private fun loadAnimation(){

        findViewById<ScrollView>(R.id.scrollBar).setVisibility(View.GONE)
        var doneIcon = findViewById<ImageView>(R.id.doneIcon)

       iconAnnimation(doneIcon)
    }

    private fun unLoadAnimation(){

        findViewById<ScrollView>(R.id.scrollBar).setVisibility(View.VISIBLE)
    }


    private fun error2save(){

        findViewById<ScrollView>(R.id.scrollBar).setVisibility(View.VISIBLE)

    }

    private fun iconAnnimation(doneIcon : ImageView){
        doneIcon.animate().apply {
            duration = 1500
            rotationYBy(360f)
        }
    }



    private fun changePasswordVisibility(){
        mNewUserViewModel.passwordVisibility()
    }


    private fun birthdayObserver(){
       var birthday = findViewById<EditText>(R.id.form_guest_age)
        birthday.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewUserViewModel.birthdayFormat(birthday.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun mailObserver(){
        var mail = findViewById<EditText>(R.id.form_guest_mail)
        mail.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                mNewUserViewModel.validateMail(mail.text.toString(), mMailListner)
            }
        }
    }

    private fun phoneObserver(){
        var phone = findViewById<EditText>(R.id.form_guest_phone)
        phone.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                mNewUserViewModel.phoneFormat(phone.text.toString())
            }
        })
    }

    private fun password2Observer(){
        var password1 = findViewById<EditText>(R.id.form_guest_password1)
        var password2 = findViewById<EditText>(R.id.form_guest_password2)
        password2.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewUserViewModel.checkPassword2(password1.text.toString(), password2.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun password1Observer(){
        var password1 = findViewById<EditText>(R.id.form_guest_password1)
        var password2 = findViewById<EditText>(R.id.form_guest_password2)
        password1.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewUserViewModel.checkPassword1(password1.text.toString(), password2.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }



    private fun saveNewUser(){

            loadAnimation()

            var name = findViewById<EditText>(R.id.form_guest_name).text.toString()
            var birthday = findViewById<EditText>(R.id.form_guest_age).text.toString()
            var cpf = findViewById<EditText>(R.id.form_guest_cpf).text.toString()
            var mail : String = findViewById<EditText>(R.id.form_guest_mail).text.toString()
            var phone = findViewById<EditText>(R.id.form_guest_phone).text.toString()
            var cep : String = findViewById<EditText>(R.id.form_guest_zipcode).text.toString()
            var uf : String = findViewById<EditText>(R.id.form_guest_State).text.toString()
            var city : String = findViewById<EditText>(R.id.form_guest_city).text.toString()
            var neighbohood : String = findViewById<EditText>(R.id.form_guest_neighborhood).text.toString()
            var address : String = findViewById<EditText>(R.id.form_guest_address).text.toString()
            var password2 = findViewById<EditText>(R.id.form_guest_password2).text.toString()

            mNewUserViewModel.saveUser(name,birthday,cpf,mail,phone,cep,uf,city,neighbohood,address,password2,mNewUserListner)


    }

    private fun listnerCPF(){
        var cpf = findViewById<EditText>(R.id.form_guest_cpf)
        cpf.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewUserViewModel.checkCPF(cpf.text.toString(),mDB_Listner)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }


    private fun listnerCEP(){
        var validateButton = findViewById<EditText>(R.id.form_guest_zipcode)

        validateButton.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                findCEP()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

    private fun listnerName(){
        var name = findViewById<EditText>(R.id.form_guest_name)

        name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewUserViewModel.checkName(name.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }

    private fun findCEP(){

        var cep : String = findViewById<EditText>(R.id.form_guest_zipcode).text.toString()
        mNewUserViewModel.validateCep(cep, mAddressListner)

    }





}