package gst.trainingcourse_ex11_thaonx4.app1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import gst.trainingcourse_ex11_thaonx4.app1.adapter.DayAdapter
import gst.trainingcourse_ex11_thaonx4.app1.databinding.ActivityMainBinding
import gst.trainingcourse_ex11_thaonx4.app1.model.Day
import gst.trainingcourse_ex11_thaonx4.app1.model.Day.Companion.FRIDAY
import gst.trainingcourse_ex11_thaonx4.app1.model.Day.Companion.MONDAY
import gst.trainingcourse_ex11_thaonx4.app1.model.Day.Companion.THURSDAY
import gst.trainingcourse_ex11_thaonx4.app1.model.Day.Companion.TUESDAY
import gst.trainingcourse_ex11_thaonx4.app1.model.Day.Companion.WEDNESDAY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dayAdapter: DayAdapter
    private var listSubject = arrayListOf<Day>()

    private var contentUri =
        Uri.parse("content://gst.trainingcourse_ex11_thaonx4.app1.provider.DayContentProvider/day_info")

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
        val values = ContentValues()

        values.put(MONDAY, "android")
        values.put(TUESDAY, "Ios")
        values.put(WEDNESDAY, "Python")
        values.put(THURSDAY, "Java")
        values.put(FRIDAY, "C++")

        contentResolver.insert(contentUri, values)
        */

        binding.loadData.setOnClickListener {

            val cursor = contentResolver.query(contentUri, null, null, null, null)

            if (cursor != null) {
                listSubject.clear()
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(0)
                    val monday = cursor.getString(1)
                    val tuesday = cursor.getString(2)
                    val wednesday = cursor.getString(3)
                    val thursday = cursor.getString(4)
                    val friday = cursor.getString(5)

                    val days = Day(id, monday, tuesday, wednesday, thursday, friday)
                    listSubject.add(days)

                    Log.d("xuanthao", "query:" + id + " - "
                            + monday + " - " + tuesday + " - " + wednesday + " - " + thursday + " - " + friday
                    )
                }
            }
            setupRecyclerview()
        }
    }
    private fun setupRecyclerview() {
        dayAdapter = DayAdapter()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dayAdapter
        }
        dayAdapter.differ.submitList(listSubject)
    }
}