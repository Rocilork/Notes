package com.example.note.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.note.Class.Class_Notes
import com.example.note.R

class Adapter_Notes(val api: ArrayList<Class_Notes>): RecyclerView.Adapter<Adapter_Notes.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val forWhat: TextView = itemView.findViewById(R.id.forWhat)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Notes.MyViewHolder {
        val listItemView = LayoutInflater.from(parent.context).inflate(R.layout.spisok_notes, parent, false)
        return MyViewHolder(listItemView)
    }

    override fun onBindViewHolder(holder: Adapter_Notes.MyViewHolder, position: Int) {
        val apis = api.get(position)
        holder.forWhat.text = apis.ДляЧего
        holder.description.text = apis.Описание
    }

    override fun getItemCount(): Int {
        return api.size
    }
}