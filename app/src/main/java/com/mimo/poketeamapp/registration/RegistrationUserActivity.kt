package com.mimo.poketeamapp.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.mimo.poketeamapp.databinding.ActivityRegisterUserBinding
import com.mimo.poketeamapp.login.afterTextChanged

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.userName
        val surname = binding.userSurname
        val email = binding.email
        val password = binding.newPassword
        val repeatedPassword = binding.repeatNewPassword
        val register = binding.register
        val loading = binding.loading

        registrationViewModel = ViewModelProvider(this, RegistrationViewModelFactory(db))
            .get(RegistrationViewModel::class.java)

        registrationViewModel.registrationFormState.observe(this@RegisterUserActivity, Observer {
            val registrationState = it ?: return@Observer

            register.isEnabled = registrationState.isDataValid

            if (registrationState.emailError != null) {
                email.error = getString(registrationState.emailError)
            }
            if (registrationState.passwordError != null) {
                password.error = getString(registrationState.passwordError)
            }
        })

        registrationViewModel.registrationResult.observe(this@RegisterUserActivity, Observer {
            val registrationResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registrationResult.error != null) {
                showRegistrationFailed(registrationResult.error)
            }
            if (registrationResult.success != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })

        name.afterTextChanged {
            registrationViewModel.registrationDataChanged(
                name.text.toString(),
                surname.text.toString(),
                email.text.toString(),
                password.text.toString(),
                repeatedPassword.text.toString()
            )
        }

        surname.afterTextChanged {
            registrationViewModel.registrationDataChanged(
                name.text.toString(),
                surname.text.toString(),
                email.text.toString(),
                password.text.toString(),
                repeatedPassword.text.toString()
            )
        }

        email.afterTextChanged {
            registrationViewModel.registrationDataChanged(
                name.text.toString(),
                surname.text.toString(),
                email.text.toString(),
                password.text.toString(),
                repeatedPassword.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registrationViewModel.registrationDataChanged(
                    name.text.toString(),
                    surname.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    repeatedPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registrationViewModel.registrate(
                            name.text.toString(),
                            surname.text.toString(),
                            email.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }
        }

        repeatedPassword.apply {
            afterTextChanged {
                registrationViewModel.registrationDataChanged(
                    name.text.toString(),
                    surname.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    repeatedPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registrationViewModel.registrate(
                            name.text.toString(),
                            surname.text.toString(),
                            email.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registrationViewModel.registrate(name.text.toString(), surname.text.toString(),
                    email.text.toString(), password.text.toString())
            }
        }
    }

    private fun showRegistrationFailed(@StringRes errorString: Int) {
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