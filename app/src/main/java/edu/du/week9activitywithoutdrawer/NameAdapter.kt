package edu.du.week9activitywithoutdrawer

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

class NameAdapter(contacts: ArrayList<Contact>, listener: (Contact) -> Unit):
    RecyclerView.Adapter<NameAdapter.ContactViewHolder>() {

    val items: ArrayList<Contact> = contacts
    val listener = listener

    inner class ContactViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(contact: Contact) {
            itemView.findViewById<TextView>(R.id.contact_name).setText(contact.name)
            itemView.findViewById<TextView>(R.id.contact_phone).setText(contact.number)

            itemView.setOnClickListener { listener.invoke(contact)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_contact, parent, false));
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(items[position])
    }
}