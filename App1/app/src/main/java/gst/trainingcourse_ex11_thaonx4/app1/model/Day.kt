package gst.trainingcourse_ex11_thaonx4.app1.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_info")
data class Day(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "monday")
    var monday: String = "",
    @ColumnInfo(name = "tuesday")
    var tuesday: String = "",
    @ColumnInfo(name = "wednesday")
    var wednesday: String = "",
    @ColumnInfo(name = "thursday")
    var thursday: String = "",
    @ColumnInfo(name = "friday")
    var friday: String = "",
) {
    fun fromContentValues(contentValues: ContentValues): Day {
        val day = Day()
        if (contentValues.containsKey(ID)) {
            day.id = contentValues.getAsInteger(ID)
        }
        if (contentValues.containsKey(MONDAY)) {
            day.monday = contentValues.getAsString(MONDAY)
        }
        if (contentValues.containsKey(TUESDAY)) {
            day.tuesday = contentValues.getAsString(TUESDAY)
        }
        if (contentValues.containsKey(WEDNESDAY)) {
            day.wednesday = contentValues.getAsString(WEDNESDAY)
        }
        if (contentValues.containsKey(THURSDAY)) {
            day.thursday = contentValues.getAsString(THURSDAY)
        }
        if (contentValues.containsKey(FRIDAY)) {
            day.friday = contentValues.getAsString(FRIDAY)
        }
        return day
    }

    companion object {
        const val ID = "id";
        const val MONDAY = "monday"
        const val TUESDAY = "tuesday"
        const val WEDNESDAY = "wednesday"
        const val THURSDAY = "thursday"
        const val FRIDAY = "friday"
    }
}
