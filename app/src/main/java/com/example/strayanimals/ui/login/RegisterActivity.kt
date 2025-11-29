package com.example.strayanimals.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.strayanimals.R
import com.example.strayanimals.databinding.ActivityLoginBinding
import com.example.strayanimals.databinding.ActivityRegisterBinding
import com.example.strayanimals.preference_Activity
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private var sqlHelper: UserSQLHelper? = null
    private  lateinit var binding: ActivityRegisterBinding
    private lateinit var loginViewModel: LoginViewModel

    private fun initSQL() {
        sqlHelper = UserSQLHelper(this)
        try {
            sqlHelper!!.readableDatabase.query("user",null,null,null,null,null,null)
        } catch(e: Exception) {
            sqlHelper!!.onCreate(sqlHelper!!.readableDatabase) //第一次开启软件要创建表单
            Log.e("helper","table created")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username=binding.username;
        var password=binding.password;
        var register=binding.reg;
        initSQL();

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@RegisterActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@RegisterActivity, Observer {
            val loginResult = it ?: return@Observer


            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
            }
//            //检查数据库
            var queryR = sqlHelper?.readSQL()
            if ((!isNullOrEmpty(username.text.toString()))&&(!isNullOrEmpty(password.text.toString()))){
                var _isRegistered =
                    queryR?.let { it1 -> addUser(it1,username.text.toString(), password.text.toString()) }
                if(_isRegistered==true){
                    Toast.makeText(applicationContext, "用户名已存在", Toast.LENGTH_LONG).show()
                }else{
                    showRegisterSuccess()
                    //跳转
                    startpreferenceActivity(username.text.toString())
                }
            }



        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

        }

    }

    private fun showRegisterSuccess(){
        Toast.makeText(applicationContext, "注册成功", Toast.LENGTH_LONG).show()
    }
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
    private fun addUser(ret:MutableList<Map<String, Any>>,userName:String,userPwd:String):Boolean{
        val _isRegistered = ret?.run {
            for(mapItem in this){0
                var _isRegistered = mapItem.containsValue(userName)
                if (_isRegistered) return@run true
            }
            false
        }
        if(_isRegistered){
            return true
        }else{
            val creatTime = System.currentTimeMillis()
            sqlHelper?.addUser(userName,userPwd,creatTime)
            return false
        }


    }

    private fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }
    private fun isregistered(ret: MutableList<Map<String, Any>>,userName:String):Boolean{
        val _isRegistered=ret?.run {
            for (mapItem in this){
                if(mapItem.containsValue(userName)) return true
            }
            false
        }
        return _isRegistered
    }
    private fun startpreferenceActivity(userName: String){
        setResult(Activity.RESULT_OK)
        //跳转到Mainactivity
        var intent = Intent(this, preference_Activity::class.java)
        intent.putExtra("username",userName)
        startActivity(intent)
        //Complete and destroy login activity once successful
        finish()
    }
}