package com.nxt.mytable2

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.nxt.mytable2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var persons = arrayListOf<Person>()
    private lateinit var personAdapter: PersonAdapter

    var CONTENT_URI: Uri = Uri.parse("content://com.nxt.mytable.TableProvider/engineer")

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoad.setOnClickListener {

            val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)

            if (cursor!!.moveToFirst()){
                //vi trí cuối cùng
                while (!cursor.isAfterLast) {

                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val monday = cursor.getString(cursor.getColumnIndex("monday"))
                    val tuesday = cursor.getString(cursor.getColumnIndex("tuesday"))
                    val wednesday = cursor.getString(cursor.getColumnIndex("wednesday"))

                    Log.d("xuanthao", "query:" + id + " - "
                            + monday + " - " + tuesday + " - " + wednesday)

                    val person = Person(id, monday, tuesday, wednesday)
                    persons.add(person)

                    cursor.moveToNext()

                }

            }else{
                Log.d("xuanthao", "null")
            }

            Log.d("xuanthao",persons.toString())

            personAdapter = PersonAdapter()
            binding.rv.layoutManager = LinearLayoutManager(this)
            binding.rv.adapter = personAdapter

            personAdapter.differ.submitList(persons)

        }
    }
}