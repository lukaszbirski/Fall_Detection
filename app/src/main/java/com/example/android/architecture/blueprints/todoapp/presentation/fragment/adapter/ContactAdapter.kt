package com.example.android.architecture.blueprints.todoapp.presentation.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.databinding.ContactItemBinding
import com.example.android.architecture.blueprints.todoapp.model.Contact

class ContactAdapter(
    private val contacts: ArrayList<Contact>,
    private val context: Context,
    private val listener: ContactAdapterListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun getItemCount() = contacts.size

    inner class ContactViewHolder(
        val binging: ContactItemBinding
    ) : RecyclerView.ViewHolder(binging.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactViewHolder(
            ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.binging.apply {
            personTextView.text = context.getString(
                R.string.template_person_name,
                contacts[position].name,
                contacts[position].surname
            )
            numberTextView.text = context.getString(
                R.string.template_phone_number,
                contacts[position].prefix,
                contacts[position].number
            )
        }

        holder.binging.removeContact.setOnClickListener {
            listener.removeContact(contacts[position])
        }
    }
}
