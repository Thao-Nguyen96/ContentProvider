package com.nxt.mytable

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nxt.mytable.TableSQLiteHelper.Companion.ID
import com.nxt.mytable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = Uri.parse("content://com.nxt.mytable.TableProvider/engineer")

        binding.btnInsert.setOnClickListener {
            val values = ContentValues()

            values.put("monday", "AndroidAndroidAndroid")
            values.put("tuesday", "AndroidAndroidAndroid")
            values.put("wednesday", "AndroidAndroidAndroid")

            contentResolver.insert(uri, values)
        }

        binding.btnDelete.setOnClickListener {

            contentResolver.delete(uri, "$ID = 2", null)
        }

        binding.btnUpdate.setOnClickListener {

            val values = ContentValues()

            values.put("monday", "Android")
            values.put("tuesday", "Java core")
            values.put("wednesday", "Java")

            contentResolver.update(uri,values, "$ID = 2",null)
        }

        binding.btnLoad.setOnClickListener {
            val cursor = contentResolver.query(uri, null, null, null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(0)
                    val monday = cursor.getString(1)
                    val tuesday = cursor.getString(2)
                    val wednesday = cursor.getString(3)

                    binding.tvMonday.text = monday
                    binding.tvTuesday.text = tuesday
                    binding.tvWednesday.text = wednesday
                    Log.d("xuanthao", "query:" + id + " - "
                            + monday + " - " + tuesday + " - " + wednesday)
                }
            }

        }
    }
}