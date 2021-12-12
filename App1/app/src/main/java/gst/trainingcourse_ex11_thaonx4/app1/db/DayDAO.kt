package gst.trainingcourse_ex11_thaonx4.app1.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gst.trainingcourse_ex11_thaonx4.app1.model.Day


@Dao
interface DayDAO {

    @Insert
    fun insert(day: Day?): Long

    @Query("SELECT * FROM day_info")
    fun findAll(): Cursor?

    @Query("DELETE FROM day_info WHERE id = :id ")
    fun delete(id: Long): Int

    @Update
    fun update(day: Day?): Int
}