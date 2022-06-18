package com.mimo.poketeamapp.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

class LocaleHelper {
    private val selectedLanguage: String = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)

        return updateResources(context, language)
    }

    private fun persist(context: Context, language: String) {
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(selectedLanguage, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale: Locale = Locale(language)
        Locale.setDefault(locale)

        val conf: Configuration = context.resources.configuration
        conf.setLocale(locale)
        conf.setLayoutDirection(locale)

        return context.createConfigurationContext(conf)
    }
}