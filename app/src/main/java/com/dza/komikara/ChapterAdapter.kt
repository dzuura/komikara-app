package com.dza.komikara

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ChapterAdapter(context: Context, private val chapters: List<String>) :
    ArrayAdapter<String>(context, 0, chapters) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false)
        val chapterTextView = view.findViewById<TextView>(R.id.txtChapter)
        chapterTextView.text = chapters[position]
        return view
    }
}