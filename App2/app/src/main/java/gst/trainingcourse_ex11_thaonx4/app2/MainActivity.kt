package gst.trainingcourse_ex11_thaonx4.app2

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import gst.trainingcourse_ex11_thaonx4.app2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val ID = "id";
        const val MONDAY = "monday"
        const val TUESDAY = "tuesday"
        const val WEDNESDAY = "wednesday"
        const val THURSDAY = "thursday"
        const val FRIDAY = "friday"
    }

    private lateinit var binding: ActivityMainBinding

    private var contentUri =
        Uri.parse("content://gst.trainingcourse_ex11_thaonx4.app1.provider.DayContentProvider/day_info")

    private lateinit var dayAdapter: DayAdapter
    private var listSubject = arrayListOf<Day>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerview()
        loadData()

        binding.btnInsert.setOnClickListener {
            showDialog()
        }
        //delete id in database table
        binding.btnDelete.setOnClickListener {
            showDeleteDialog()
        }
        binding.btnUpdate.setOnClickListener {
            showUpdateDialog()
        }

        binding.btnLoadData.setOnClickListener {
            loadData()
            setupRecyclerview()
        }
    }

    private fun showUpdateDialog() {

        val dialog = Dialog(this)

        dialog.apply {
            setCancelable(false)
            setContentView(R.layout.update_dialog)
        }

        val edtId = dialog.findViewById(R.id.edt_id_update_dialog) as EditText
        val edtMonday = dialog.findViewById(R.id.edt_monday_update_dialog) as EditText
        val edtTuesday = dialog.findViewById(R.id.edt_tuesday_update_dialog) as EditText
        val edtWednesday = dialog.findViewById(R.id.edt_wednesday_update_dialog) as EditText
        val edtThursday = dialog.findViewById(R.id.edt_thursday_update_dialog) as EditText
        val edtFriday = dialog.findViewById(R.id.edt_friday_update_dialog) as EditText

        val btnUpdate = dialog.findViewById(R.id.btn_update_dialog) as Button
        val btnCancel = dialog.findViewById(R.id.btn_cancel_update_dialog) as Button

        btnUpdate.setOnClickListener {
            val id = edtId.text.toString()
            val monday = edtMonday.text.toString()
            val tuesday = edtTuesday.text.toString()
            val wednesday = edtWednesday.text.toString()
            val thursday = edtThursday.text.toString()
            val friday = edtFriday.text.toString()

            val values = ContentValues()

            values.put(ID,id)
            values.put(MONDAY, monday)
            values.put(TUESDAY, tuesday)
            values.put(WEDNESDAY, wednesday)
            values.put(THURSDAY, thursday)
            values.put(FRIDAY, friday)

            val uriId =
                Uri.parse("content://gst.trainingcourse_ex11_thaonx4.app1.provider.DayContentProvider/day_info/$id")
            contentResolver.update(uriId, values, null, null)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDeleteDialog() {
        val dialog = Dialog(this)
        dialog.apply {
            setCancelable(false)
            setContentView(R.layout.delete_dialog)
        }

        val edtId = dialog.findViewById(R.id.edt_id_dialog_delete) as EditText
        val btnDelete = dialog.findViewById(R.id.btn_delete_dialog) as Button
        val btnCancel = dialog.findViewById(R.id.btn_cancel_delete_dialog) as Button

        btnDelete.setOnClickListener {
            val id = edtId.text.toString()

            val uriId =
                Uri.parse("content://gst.trainingcourse_ex11_thaonx4.app1.provider.DayContentProvider/day_info/$id")
            contentResolver.delete(uriId, null, null)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDialog() {

        val dialog = Dialog(this).apply {
            setCancelable(false)
            setContentView(R.layout.insert_dialog)
        }

        val edtMonday = dialog.findViewById(R.id.edt_monday_dialog) as EditText
        val edtTuesday = dialog.findViewById(R.id.edt_tuesday_dialog) as EditText
        val edtWednesday = dialog.findViewById(R.id.edt_wednesday_dialog) as EditText
        val edtThursday = dialog.findViewById(R.id.edt_thursday_dialog) as EditText
        val edtFriday = dialog.findViewById(R.id.edt_friday_dialog) as EditText

        val btnAdd = dialog.findViewById(R.id.btn_add) as Button
        val btnCancel = dialog.findViewById(R.id.btn_cancel) as Button

        btnAdd.setOnClickListener {

            val monday = edtMonday.text.toString()
            val tuesday = edtTuesday.text.toString()
            val wednesday = edtWednesday.text.toString()
            val thursday = edtThursday.text.toString()
            val friday = edtFriday.text.toString()

            val values = ContentValues()

            values.put(MONDAY, monday)
            values.put(TUESDAY, tuesday)
            values.put(WEDNESDAY, wednesday)
            values.put(THURSDAY, thursday)
            values.put(FRIDAY, friday)

            contentResolver.insert(contentUri, values)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("Range")
    private fun loadData() {

        listSubject.clear()

        val cursor = contentResolver.query(contentUri, null, null, null, null)

        if (cursor!!.moveToFirst()) {
            //vi trí cuối cùng
            while (!cursor.isAfterLast) {

                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val monday = cursor.getString(cursor.getColumnIndex(MONDAY))
                val tuesday = cursor.getString(cursor.getColumnIndex(TUESDAY))
                val wednesday = cursor.getString(cursor.getColumnIndex(WEDNESDAY))
                val thursday = cursor.getString(cursor.getColumnIndex(THURSDAY))
                val friday = cursor.getString(cursor.getColumnIndex(FRIDAY))

                Log.d("xuanthao", "query:" + id + " - "
                        + monday + " - " + tuesday + " - " + wednesday + "-" + thursday
                        + " _ " + friday)

                val subject = Day(id, monday, tuesday, wednesday, thursday, friday)
                listSubject.add(subject)

                cursor.moveToNext()
            }
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