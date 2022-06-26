package com.mimo.poketeamapp.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mimo.poketeamapp.LocalizationActivity
import com.mimo.poketeamapp.MainActivity
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.databinding.ActivityLoginBinding
import com.mimo.poketeamapp.forgotPassword.ForgotPasswordActivity
import com.mimo.poketeamapp.registration.RegisterUserActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.util.*


@DelicateCoroutinesApi
class LoginActivity : LocalizationActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var imageUri: String
    private var lang: String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val username = binding.username
        val password = binding.password
        val forgotPassword = binding.forgotPassword
        val registerUser = binding.signUp
        val login = binding.login
        val loading = binding.loading
        val picture = binding.profilePictureLogin

        GlobalScope.launch(Dispatchers.Main) {
            dataStoreManager.getFromDataStore().catch { e ->
                e.printStackTrace()
            }.collect {
                withContext(Dispatchers.Main) {
                    imageUri = it.image
                    lang = it.preferredLanguage
                    setAppLocale(this@LoginActivity, lang)
                    refreshLoginResources()
                }
            }
        }

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(db))
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                val user = db.userDao().getUser(username.text.toString(), password.text.toString())

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", username.text.toString())
                intent.putExtra("password", password.text.toString())
                intent.putExtra("imageUri", imageUri)
                intent.putExtra("language", lang)
                startActivity(intent)
            }
        })

        Picasso.get().load(R.drawable.pokemon_logo).into(picture)

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

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
            
            setOnTouchListener { v, event ->
                val drawableRight = 2

                if(event.action == MotionEvent.ACTION_UP) {
                    if(event.rawX >= (password.right - password.compoundDrawables[drawableRight].bounds.width())) {
                        if(password.inputType.toString() == InputType.TYPE_CLASS_TEXT.toString()) {
                            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        } else {
                            password.inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }
                }
                false
            }
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        registerUser.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun refreshLoginResources() {
        binding.username.hint = getString(R.string.prompt_email)
        binding.password.hint = getString(R.string.prompt_password)
        binding.forgotPassword.text = getString(R.string.forgot_password)
        binding.login.text = getString(R.string.action_sign_in_short)
        binding.stillNotConnected.text = getString(R.string.still_not_connected)
        binding.signUp.text = getString(R.string.sign_up)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}