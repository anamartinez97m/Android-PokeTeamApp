package com.mimo.poketeamapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class LocaleApp : Application() {
    override fun onCreate() {
//        updateLanguage(this)
        super.onCreate()
    }

    private fun updateLanguage(ctx: Context) {
        val lang = "en"
        updateLanguage(ctx, lang)
    }

    private fun updateLanguage(ctx: Context, lang: String) {
        val cfg = Configuration()
        if (!TextUtils.isEmpty(lang)) cfg.setLocale(Locale(lang)) else cfg.setLocale(Locale.getDefault())
        ctx.resources.updateConfiguration(cfg, null)
    }
}
