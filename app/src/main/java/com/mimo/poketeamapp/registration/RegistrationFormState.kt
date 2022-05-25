package com.mimo.poketeamapp.registration

data class RegistrationFormState(val emailError: Int? = null,
                        val passwordError: Int? = null,
                        val repeatedPasswordError: Int? = null,
                        val isDataValid: Boolean = false)