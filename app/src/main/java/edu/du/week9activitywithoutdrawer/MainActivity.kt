package edu.du.week9activitywithoutdrawer

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<Contact>()
    private var adapter: NameAdapter? = null
    private var useDrawer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        adapter = NameAdapter(list) { contact: Contact ->
            if (useDrawer) {
                showDrawer(contact)
            } else {
                showDialog(contact)
            }

        }

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { _ ->
            if (useDrawer) {
                showDrawer(null)
            } else {
                showDialog(null)
            }
        }
    }

    private fun showDialog(contact: Contact?) {
        val dialogBuilder = AlertDialog.Builder(this)
        val layout = LayoutInflater.from(this).inflate(R.layout.view_dialog, null)
        dialogBuilder.setView(layout)
        if (contact != null) {
            dialogBuilder.setTitle("Update Contact")
        } else {
            dialogBuilder.setTitle("Add Contact")
        }

        val dialog: Dialog = dialogBuilder.create()
        connectViews(layout, contact, dialog)

        dialog.show()
    }
    private fun showDrawer(contact: Contact?) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        val layout = LayoutInflater.from(this).inflate(R.layout.view_dialog, null)
        bottomSheetDialog.setContentView(layout)
        connectViews(layout, contact, bottomSheetDialog)
        bottomSheetDialog.show()
    }
    private fun connectViews(layout: View, contact: Contact?, dialog: Dialog) {
        val editName = layout.findViewById<EditText>(R.id.edit_name)
        val editPhone = layout.findViewById<EditText>(R.id.edit_phone)
        if (contact != null) {
            editName.text.append(contact.name)
            editPhone.text.append(contact.number)
        }
        layout.findViewById<Button>(R.id.button_save).setOnClickListener {
            val newName = editName.text.toString()
            val newNumber = editPhone.text.toString()
            if (contact == null) {
                list.add(Contact(newName, newNumber))
            } else {
                contact.name = newName
                contact.number = newNumber
            }
            adapter?.notifyDataSetChanged()
            dialog.hide()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_input -> {
                useDrawer = !useDrawer
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}