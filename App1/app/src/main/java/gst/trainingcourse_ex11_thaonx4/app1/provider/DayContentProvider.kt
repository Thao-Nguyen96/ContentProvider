package gst.trainingcourse_ex11_thaonx4.app1.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import gst.trainingcourse_ex11_thaonx4.app1.db.ApplicationDatabase
import gst.trainingcourse_ex11_thaonx4.app1.db.DayDAO
import gst.trainingcourse_ex11_thaonx4.app1.model.Day

class DayContentProvider : ContentProvider() {

    private var dayDAO: DayDAO? = null

    companion object {
        const val TAG = "xuanthao"

        const val AUTHORITY =
            "gst.trainingcourse_ex11_thaonx4.app1.provider.DayContentProvider"

        const val DAY_TABLE_NAME = "day_info"

        const val ID_DAY_DATA = 1
        const val ID_DAY_DATA_ITEM = 2
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY,
            DAY_TABLE_NAME,
            ID_DAY_DATA);
        uriMatcher.addURI(AUTHORITY,
            DAY_TABLE_NAME +
                    "/*", ID_DAY_DATA_ITEM);
    }

    override fun onCreate(): Boolean {
        dayDAO = ApplicationDatabase.getInstance(context)!!.getDayDAO()
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor {
        Log.d(TAG, "query")
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_DAY_DATA -> {
                cursor = dayDAO!!.findAll()
                if (context != null) {
                    cursor!!.setNotificationUri(context!!
                        .contentResolver, uri)
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        Log.d(TAG, "insert")
        when (uriMatcher.match(uri)) {
            ID_DAY_DATA -> {
                if (context != null) {
                    val id = dayDAO!!.insert(Day().fromContentValues(values!!))
                    if (id != 0L) {
                        context!!.contentResolver
                            .notifyChange(uri, null)
                        return ContentUris.withAppendedId(uri, id)
                    }
                }
                throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            ID_DAY_DATA_ITEM -> throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete")
        when (uriMatcher.match(uri)) {
            ID_DAY_DATA -> throw IllegalArgumentException("Invalid uri: cannot delete")
            ID_DAY_DATA_ITEM -> {
                if (context != null) {
                    val count = dayDAO
                        ?.delete(ContentUris.parseId(uri))
                    context!!.contentResolver
                        .notifyChange(uri, null)
                    return count!!
                }
                throw IllegalArgumentException("Unknown URI:$uri")
            }
            else -> throw IllegalArgumentException("Unknown URI:$uri")
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        when (uriMatcher.match(uri)) {
            ID_DAY_DATA -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            ID_DAY_DATA_ITEM -> {
                if (context != null) {
                    val count = dayDAO?.update(Day().fromContentValues(values!!))
                    context!!.contentResolver.notifyChange(uri, null)
                    return count!!
                }
                throw IllegalArgumentException("Unknown URI:$uri")

            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}