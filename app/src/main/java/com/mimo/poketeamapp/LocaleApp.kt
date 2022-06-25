package com.mimo.poketeamapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.text.TextUtils
import androidx.preference.PreferenceManager
import com.mimo.poketeamapp.settings.LocaleHelper
import java.util.*


class LocaleApp : Application() {
    override fun onCreate() {
        updateLanguage(this);
        super.onCreate()
//        val localeHelper = LocaleHelper()
//        localeHelper.setLocale(
//            Locale(
//                localeHelper.getPrefLangCode(
//                    this
//                ), localeHelper.getPrefCountryCode(this)
//            )
//        )
//        localeHelper.updateConfiguration(this, resources.configuration)
    }

    fun updateLanguage(ctx: Context) {
        val lang = "en"
        updateLanguage(ctx, lang)
    }

    fun updateLanguage(ctx: Context, lang: String) {
        val cfg = Configuration()
        if (!TextUtils.isEmpty(lang)) cfg.setLocale(Locale(lang)) else cfg.setLocale(Locale.getDefault())
        ctx.resources.updateConfiguration(cfg, null)
    }
}
