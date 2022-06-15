package com.mimo.poketeamapp.settings

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mimo.poketeamapp.R

class CustomExpandableListAdapter(
    private val context: Context,
    private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

    private val childTypeLanguage = 0
    private val childTypeUserData = 1
    private val childTypeProfilePicture = 2
    private val childTypeUndefined = 3

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.expandableListDetail[this.expandableListTitle[listPosition]]
            ?.get(expandedListPosition) ?: ArrayList<String>();
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertViewVar: View? = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childType = getChildType(listPosition, expandedListPosition)

        if (convertViewVar == null) {
            when (childType) {
                0 -> convertViewVar = layoutInflater.inflate(R.layout.list_item_language, null)
                1 -> convertViewVar = layoutInflater.inflate(R.layout.list_item_user_data, null)
                2 -> convertViewVar = layoutInflater.inflate(R.layout.list_item_profile_picture, null)
            }
        }

        when (childType) {
            0 -> {
                val expandedListTextView = convertViewVar?.findViewById(R.id.expanded_language_list_item) as TextView
                expandedListTextView.text = expandedListText
            }
            1 -> {
                val expandedListTextView = convertViewVar?.findViewById(R.id.expanded_user_data_list_item) as TextView
                expandedListTextView.text = expandedListText
            }
            2 -> {
                val expandedListImageView = convertViewVar?.findViewById(R.id.profile_picture_settings) as ImageView
            }
        }

        return convertViewVar
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]?.size ?: 0
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup?): View {
        var convertViewVar: View? = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertViewVar == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewVar = layoutInflater.inflate(R.layout.list_group, parent, false)
        }
        val listTitleTextView = convertViewVar?.findViewById(R.id.list_title) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertViewVar
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    override fun getChildTypeCount(): Int {
        return 3
    }

    override fun getChildType(groupPosition: Int, childPosition: Int): Int {
        return when (groupPosition) {
            0 -> childTypeLanguage
            1 -> childTypeUserData
            2 -> childTypeProfilePicture
            else -> childTypeUndefined
        }
    }
}