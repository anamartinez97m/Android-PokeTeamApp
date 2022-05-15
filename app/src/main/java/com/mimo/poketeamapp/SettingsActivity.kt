package com.mimo.poketeamapp

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    private var expandableListTitle: List<String> = ArrayList()
    private var expandableListDetail: HashMap<String, List<String>> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_settings)

        val expandableListView: ExpandableListView = findViewById(R.id.expandable_list_view)
//        expandableListDetail: HashMap<String, List<String>> = this.addDataToHashMap()
//        expandableListTitle = ArrayList<String>(expandableListDetail.keys)
        addDataToHashMap()
        val expandableListAdapter: ExpandableListAdapter =
            CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)
//        expandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
//            expandableListView.expandGroup(groupPosition)
//            false
//        }

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
//            Toast.makeText(applicationContext, (expandableListTitle[groupPosition] + " -> "
//                        + expandableListDetail[expandableListTitle[groupPosition]]!![childPosition]),
//                Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun addDataToHashMap() {
        expandableListTitle = expandableListTitle + getString(R.string.change_language)
        expandableListTitle = expandableListTitle + getString(R.string.modify_user_data)

        var languages: List<String> = ArrayList()
        languages = languages + getString(R.string.language_english)
        languages = languages + getString(R.string.language_spanish)
        var userData: List<String> = ArrayList()
        userData = userData + getString(R.string.prompt_email)
        userData = userData + getString(R.string.prompt_password)

        expandableListDetail[expandableListTitle[0]] = languages
        expandableListDetail[expandableListTitle[1]] = userData
    }

}