package com.example.gotamagica.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gotamagica.MainActivity
import com.example.gotamagica.R
import com.example.gotamagica.Services.Constants.ConstantsURL
import com.example.gotamagica.Services.Repository.Listner.DB_Listner
import com.example.gotamagica.Services.Repository.Local.SecurityPreferences
import com.example.gotamagica.Services.Repository.Remote.db_API.API_Data_Db
import com.example.gotamagica.ViewModel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLoginViewModel: LoginViewModel
    private lateinit var mDB_Listner : DB_Listner
    private lateinit var mSecurityPreferences : SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mSecurityPreferences = SecurityPreferences(this)

        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if(supportActionBar != null){
            supportActionBar!!.hide()
        }

        val newUser = findViewById<TextView>(R.id.newUser)
        newUser.setOnClickListener {
            var intent = Intent(applicationContext, NewUserActivity::class.java)
            clearInputData()
            startActivity(intent)
        }

        mDB_Listner = object : DB_Listner {
            override fun onSuccess(body: API_Data_Db, requestType: String) {
                super.onSuccess(body, requestType)
                mLoginViewModel.callAPI_Type(body,requestType, mSecurityPreferences)
            }

            override fun onFailure(requestType: String) {
                super.onFailure(requestType)
                Toast.makeText(applicationContext,"ERROR TO CONNECT TO SERVER", Toast.LENGTH_LONG).show()
            }
        }

        mLoginViewModel.mailListner.observe(this,{
            var mail = findViewById<EditText>(R.id.email)
            var text = findViewById<TextView>(R.id.email_notFound)

            if(!it){
                mail.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
                text.setVisibility(View.VISIBLE)
            }else{
                mail.background.setTint(ContextCompat.getColor(this,R.color.background_input))
                text.setVisibility(View.GONE)
            }
        })

        mLoginViewModel.passwordListner.observe(this,{
            var password = findViewById<EditText>(R.id.password)

            if(!it){
                password.background.setTint(ContextCompat.getColor(this,R.color.errorBox))
            }else{
                password.background.setTint(ContextCompat.getColor(this,R.color.background_input))
            }
        })

        mLoginViewModel.LoginSuccess.observe(this,{

                if(it){

                    homePage()
                }else{
                    findViewById<TextView>(R.id.loginError).setVisibility(View.VISIBLE)
                    unLoadAnimation()
                }
        })

        mLoginViewModel.AutoLogin.observe(this,{
            if(it){
                autoLogin()
            }
        })


        findViewById<AppCompatButton>(R.id.login).setOnClickListener(this)


        emailObserver()
        passwordObserver()
        verifyUserLogged()


    }

    override fun onClick(v: View) {
            var id = v.id
        when(id){
            R.id.login -> login()
        }

    }

    private fun verifyUserLogged(){
        mLoginViewModel.verifyLogged()
    }

    private fun homePage(){

        var intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun clearInputData(){
        findViewById<AppCompatEditText>(R.id.email).setText("")
        findViewById<AppCompatEditText>(R.id.password).setText("")

    }




    private fun loadAnimation(){
        findViewById<ProgressBar>(R.id.loginProgressBar).setVisibility(View.VISIBLE)
    }
    private fun unLoadAnimation(){
        findViewById<ProgressBar>(R.id.loginProgressBar).setVisibility(View.GONE)
    }



    private fun login(){

        loadAnimation()

        var email = findViewById<AppCompatEditText>(R.id.email).text.toString()
        var password = findViewById<AppCompatEditText>(R.id.password).text.toString()

        mLoginViewModel.login(email, password,mDB_Listner)
    }

    private fun autoLogin(){
        var email = mSecurityPreferences.get(ConstantsURL.SEC_PREFERENCES.SEC_MAIL)
        var psw = mSecurityPreferences.get(ConstantsURL.SEC_PREFERENCES.SEC_PSW)
        mLoginViewModel.mainCheck(email,mDB_Listner)
        mLoginViewModel.checkPassword(psw)

        mLoginViewModel.autoLogin(email, psw,mDB_Listner)
    }


    private fun emailObserver(){
        var email = findViewById<AppCompatEditText>(R.id.email)
        email.setOnFocusChangeListener { v, hasFocus ->

            if(!hasFocus){
                mLoginViewModel.mainCheck(email.text.toString(),mDB_Listner)
            }

        }
    }

    fun passwordObserver(){
        var password = findViewById<AppCompatEditText>(R.id.password)
        password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mLoginViewModel.checkPassword(password.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }






}