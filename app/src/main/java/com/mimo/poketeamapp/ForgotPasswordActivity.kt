package com.mimo.poketeamapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

class ForgotPasswordActivity : AppCompatActivity() {

    //private lateinit var newPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //newPassword = findViewById(R.id.new_password)
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        super.onTouchEvent(event)
//
//        when (event?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                return true
//            }
//            MotionEvent.ACTION_UP -> {
//                newPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//                return true
//            }
//        }
//        return false
//    }
}