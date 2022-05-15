package com.mimo.poketeamapp

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class CustomExpandableListAdapter(
    private val context: Context,
    private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
            ?.get(expandedListPosition) ?: ArrayList<String>();
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertViewVar: View? = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        Log.d("hola", expandedListText)
        if (convertViewVar == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewVar = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertViewVar?.findViewById(R.id.expanded_list_item) as TextView
        expandedListTextView.text = expandedListText
        return convertViewVar
    }

    override fun getChildrenCount(listPosition: Int): Int {
        Log.d("hola", (expandableListDetail[expandableListTitle[listPosition]]?.size ?: 0).toString())
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

}