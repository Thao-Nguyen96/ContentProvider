package gst.trainingcourse_ex11_thaonx4.app1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gst.trainingcourse_ex11_thaonx4.app1.model.Day


@Database(entities = [Day::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getDayDAO(): DayDAO?

    companion object {
        const val DATABASE_NAME = "testdb"
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context?): ApplicationDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context!!, ApplicationDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }

}