package com.mimo.poketeamapp.forgotPassword

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mimo.poketeamapp.MainActivity
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.databinding.ActivityForgotPasswordBinding
import com.mimo.poketeamapp.login.LoginActivity
import com.mimo.poketeamapp.login.afterTextChanged

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    private lateinit var binding: ActivityForgotPasswordBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.email
        val password = binding.newPassword
        val repeatedPassword = binding.repeatNewPassword
        val updatePassword = binding.updatePassword
        val loading = binding.loading

        forgotPasswordViewModel = ViewModelProvider(this, ForgotPasswordViewModelFactory(db))
            .get(ForgotPasswordViewModel::class.java)

        forgotPasswordViewModel.forgotPasswordFormState.observe(this@ForgotPasswordActivity, Observer {
            val forgotPasswordState = it ?: return@Observer

            updatePassword.isEnabled = forgotPasswordState.isDataValid

            if (forgotPasswordState.emailError != null) {
                email.error = getString(forgotPasswordState.emailError)
            }
            if (forgotPasswordState.passwordError != null) {
                password.error = getString(forgotPasswordState.passwordError)
            }
        })

        forgotPasswordViewModel.forgotPasswordResult.observe(this@ForgotPasswordActivity, Observer {
            val forgotPasswordResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (forgotPasswordResult.error != null) {
                showUpdatePasswordFailed(forgotPasswordResult.error)
            }
            if (forgotPasswordResult.success != null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })

        email.afterTextChanged {
            forgotPasswordViewModel.forgotPasswordDataChanged(
                email.text.toString(),
                password.text.toString(),
                repeatedPassword.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                forgotPasswordViewModel.forgotPasswordDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    repeatedPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        forgotPasswordViewModel.updatePassword(
                            email.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            setOnTouchListener { v, event ->
                val drawableLeft = 0
                val drawableTop = 1
                val drawableRight = 2
                val drawableBottom = 3

                if(event.action == MotionEvent.ACTION_UP) {
                    if(event.rawX >= (password.right - password.compoundDrawables[drawableRight].bounds.width())) {
                        if(password.inputType == InputType.TYPE_CLASS_TEXT) {
                            password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                        } else {
                            password.inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }
                }
                false
            }
        }

        repeatedPassword.apply {
            afterTextChanged {
                forgotPasswordViewModel.forgotPasswordDataChanged(
                    email.text.toString(),
                    password.text.toString(),
                    repeatedPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        forgotPasswordViewModel.updatePassword(
                            email.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            updatePassword.setOnClickListener {
                loading.visibility = View.VISIBLE
                forgotPasswordViewModel.updatePassword(email.text.toString(), password.text.toString())
            }

            setOnTouchListener { v, event ->
                val drawableRight = 2

                if(event.action == MotionEvent.ACTION_UP) {
                    if(event.rawX >= (repeatedPassword.right - repeatedPassword.compoundDrawables[drawableRight].bounds.width())) {
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
    }

    private fun showUpdatePasswordFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
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