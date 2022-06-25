package com.mimo.poketeamapp

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.mimo.poketeamapp.settings.LocaleHelper


abstract class LocalizationActivity : AppCompatActivity() {
    // We only override onCreate
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    init {
        val localeHelper = LocaleHelper()
        localeHelper.updateConfiguration(this)
    }
}