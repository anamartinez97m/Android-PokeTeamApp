package com.mimo.poketeamapp.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.databinding.ActivityRegisterUserBinding
import com.mimo.poketeamapp.login.afterTextChanged

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var binding: ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.userName
        val surname = binding.userSurname
        val email = binding.email
        val password = binding.newPassword
        val repeatedPassword = binding.repeatNewPassword

        registrationViewModel = ViewModelProvider(this, RegistrationViewModelFactory())
            .get(RegistrationViewModel::class.java)

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