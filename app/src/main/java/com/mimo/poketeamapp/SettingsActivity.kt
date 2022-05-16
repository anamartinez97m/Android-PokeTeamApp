package com.mimo.poketeamapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SettingsActivity : AppCompatActivity() {

    private var expandableListTitle: List<String> = ArrayList()
    private var expandableListDetail: HashMap<String, List<String>> = HashMap()
    private var languages: List<String> = ArrayList()
    private var languagesCodesMap: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //supportActionBar?.setIcon(R.drawable.ic_baseline_arrow_back_ios_24)


        val expandableListView: ExpandableListView = findViewById(R.id.expandable_list_view)
        addDataToHashMap()

        val expandableListAdapter: ExpandableListAdapter =
            CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnGroupExpandListener { groupPosition ->
//            Toast.makeText(applicationContext,
//                expandableListTitle[groupPosition] + " List Expanded.",
//                Toast.LENGTH_SHORT).show()
        }

        expandableListView.setOnGroupCollapseListener { groupPosition ->
//            Toast.makeText(applicationContext,
//                expandableListTitle[groupPosition] + " List Collapsed.",
//                Toast.LENGTH_SHORT).show()
        }

        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, _ ->
            if(groupPosition == 0) {
                changeLanguage(childPosition)
            }
            false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun addDataToHashMap() {
        expandableListTitle = expandableListTitle + getString(R.string.change_language)
        expandableListTitle = expandableListTitle + getString(R.string.modify_user_data)

        languages = languages + getString(R.string.en_US)
        languages = languages + getString(R.string.es_ES)
        languagesCodesMap[getString(R.string.en_US)] = "en_US"
        languagesCodesMap[getString(R.string.es_ES)] = "es_ES"
        var userData: List<String> = ArrayList()
        userData = userData + getString(R.string.prompt_email)
        userData = userData + getString(R.string.prompt_password)

        expandableListDetail[expandableListTitle[0]] = languages
        expandableListDetail[expandableListTitle[1]] = userData
    }

    private fun changeLanguage(languagePosition: Int) {
        // TODO: terminar
        val currentLocale = this.resources.configuration.locales[0]
        val langCode = languagesCodesMap[languages[languagePosition]]
        val conf: Configuration = this.resources.configuration
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        conf.setLocale(locale)
        createConfigurationContext(conf)
        //this.setContentView(R.layout.settings_activity)
    }

}