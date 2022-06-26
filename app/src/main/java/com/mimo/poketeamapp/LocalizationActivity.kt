package com.mimo.poketeamapp

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.mimo.poketeamapp.data.DataStoreManager
import com.mimo.poketeamapp.settings.LocaleHelper

abstract class LocalizationActivity : AppCompatActivity() {
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(applicationContext)
    }

    init {
        val localeHelper = LocaleHelper()
        localeHelper.updateConfiguration(this)
    }
}