package com.mimo.poketeamapp.forgotPassword

data class ForgotPasswordFormState(val emailError: Int? = null,
                                    val passwordError: Int? = null,
                                    val repeatedPasswordError: Int? = null,
                                    val isDataValid: Boolean = false)