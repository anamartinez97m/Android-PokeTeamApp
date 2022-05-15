package com.mimo.poketeamapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

//        val changeLanguageButton: Button = findViewById(R.id.change_language_button)
//        changeLanguageButton.setOnClickListener {
//            Log.d("button", "estas cambiando el idioma")
//        }
        val expandableListView: ExpandableListView = findViewById(R.id.expandable_list_view)
    }

}