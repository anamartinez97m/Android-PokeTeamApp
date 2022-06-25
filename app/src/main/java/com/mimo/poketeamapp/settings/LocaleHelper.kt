package com.mimo.poketeamapp.settings

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.view.ContextThemeWrapper
import androidx.preference.PreferenceManager
import com.mimo.poketeamapp.LocalizationActivity
import java.util.*


class LocaleHelper {
//    private val selectedLanguage: String = "Locale.Helper.Selected.Language"
//
//    fun setLocale(context: Context, language: String): Context {
//        persist(context, language)
//
//        return updateResources(context, language)
//    }
//
//    private fun persist(context: Context, language: String) {
//        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        val editor: SharedPreferences.Editor = pref.edit()
//        editor.putString(selectedLanguage, language)
//        editor.apply()
//    }
//
//    private fun updateResources(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val conf: Configuration = context.resources.configuration
//        conf.setLocale(locale)
//        conf.setLayoutDirection(locale)
//
//        return context.createConfigurationContext(conf)
//    }
private var mLocale: Locale? = null

    fun setLocale(locale: Locale) {
        mLocale = locale
        if (mLocale != null) {
            Locale.setDefault(mLocale)
        }
    }

    fun updateConfiguration(wrapper: LocalizationActivity) {
        if (mLocale != null) {
            val configuration = Configuration()
            configuration.setLocale(mLocale)
            wrapper.applyOverrideConfiguration(configuration)
        }
    }

    fun updateConfiguration(application: Application, configuration: Configuration?) {
        if (mLocale != null) {
            val config = Configuration(configuration)
            config.setLocale(mLocale)
            val res: Resources = application.baseContext.resources
            res.updateConfiguration(configuration, res.displayMetrics)
        }
    }

    fun updateConfiguration(context: Context, language: String?, country: String?) {
        val locale = Locale(language, country)
        setLocale(locale)
        if (mLocale != null) {
            val res: Resources = context.resources
            val configuration: Configuration = res.configuration
            configuration.setLocale(mLocale)
            res.updateConfiguration(configuration, res.displayMetrics)
        }
    }


    fun getPrefLangCode(context: Context?): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("lang_code", "es")
    }

    fun setPrefLangCode(context: Context?, mPrefLangCode: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString("lang_code", mPrefLangCode)
        editor.apply()
    }

    fun getPrefCountryCode(context: Context?): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString("country_code", "ES")
    }

    fun setPrefCountryCode(context: Context?, mPrefCountryCode: String?) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString("country_code", mPrefCountryCode)
        editor.apply()
    }
}