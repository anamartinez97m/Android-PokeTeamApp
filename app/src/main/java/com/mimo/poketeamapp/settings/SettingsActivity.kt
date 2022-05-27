package com.mimo.poketeamapp.settings

import android.content.Intent
import androidx.lifecycle.Observer
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mimo.poketeamapp.MainActivity
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.login.LoginViewModel
import com.mimo.poketeamapp.login.LoginViewModelFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SettingsActivity : AppCompatActivity() {

    private var expandableListTitle: List<String> = ArrayList()
    private var expandableListDetail: HashMap<String, List<String>> = HashMap()
    private var languages: List<String> = ArrayList()
    private var languagesCodesMap: HashMap<String, String> = HashMap()

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(db))
            .get(LoginViewModel::class.java)

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
            } else if (groupPosition == 1) {

            }
            false
        }

        /*loginViewModel.loggedInUser.observe(this@SettingsActivity, Observer {
            val result = it ?: return@Observer

            Log.d("user", result.toString())
        })*/
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
        //this.setContentView(R.layout.activity_settings)
    }

}