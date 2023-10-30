package com.example.detailsapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var database: DetailsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Room.databaseBuilder(applicationContext, DetailsDatabase::class.java,"detailsDb").fallbackToDestructiveMigration().build()
        listView = findViewById(R.id.listView)
        val addbtn = findViewById<FloatingActionButton>(R.id.add)
        val help = findViewById<ImageView>(R.id.help)

        help.setOnClickListener{
            startActivity(Intent(this,Help::class.java))
        }

        getData(listView)

        listView.setOnItemClickListener{ parent, view, position, id ->
            val view = parent.get(position)
            val id = view.findViewById<TextView>(R.id.idListItem).text.toString().toLong()
            val name = view.findViewById<TextView>(R.id.nameListItem).text.toString()
            val phone = view.findViewById<TextView>(R.id.phoneListItem).text.toString()
            val age = view.findViewById<TextView>(R.id.ageListItem).text.toString()

            var builder = AlertDialog.Builder(this)
            builder.setTitle("Edit")
            var linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL

            val idView = EditText(this)
            idView.setText(id.toString())
            linearLayout.addView(idView)

            val nameView = EditText(this)
            nameView.text.clear()
            nameView.setText(name)
            linearLayout.addView(nameView)

            val phoneView = EditText(this)
            phoneView.text.clear()
            phoneView.setText(phone)
            linearLayout.addView(phoneView)

            val ageView = EditText(this)
            ageView.text.clear()
            ageView.setText(age)
            linearLayout.addView(ageView)
            //Toast.makeText(this, "Updated $updatedName $updatedPhone", Toast.LENGTH_SHORT).show()
            builder.setView(linearLayout)

            builder.setPositiveButton("Edit", DialogInterface.OnClickListener{
                    dialog,which ->
                val updatedName = nameView.text.toString()
                val updatedPhone = phoneView.text.toString()
                val updatedAge = ageView.text.toString()
                GlobalScope.launch{
                    database.DetailsDAO().update(Details(id,updatedName,updatedPhone,updatedAge))
                }
                Toast.makeText(this, "Updated $updatedName $updatedPhone $updatedAge", Toast.LENGTH_SHORT).show()
            })

            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which -> dialog.cancel()})

            builder.show()


        }
        listView.setOnItemLongClickListener{
                parent, view, position, id ->
            val view = parent.get(position)
            val id = view.findViewById<TextView>(R.id.idListItem).text.toString().toLong()
            val name = view.findViewById<TextView>(R.id.nameListItem).text.toString()
            val phone = view.findViewById<TextView>(R.id.phoneListItem).text.toString()
            val age = view.findViewById<TextView>(R.id.ageListItem).text.toString()
            GlobalScope.launch {
                database.DetailsDAO().delete(Details(id,name,phone,age))
            }
            return@setOnItemLongClickListener true
        }


        addbtn.setOnClickListener{
            val intent= Intent(this,add::class.java)
            startActivity(intent)
        }


    }
    fun getData(view: View) {
        database.DetailsDAO().getContact().observe(this) {
            val adapter = MyAdapter(this,R.layout.list_item,it)
            listView.adapter=adapter
        }
    }
}
