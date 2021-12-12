package com.nxt.mytable

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log
import com.nxt.mytable.TableSQLiteHelper.Companion.ENGINEER_TABLE
import com.nxt.mytable.TableSQLiteHelper.Companion.ID
import java.lang.IllegalArgumentException

class TableProvider : ContentProvider() {


    companion object {

        private const val AUTHORITY = "com.nxt.mytable.TableProvider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/engineer")
        private lateinit var URI_MATCHER: UriMatcher

        private const val URI_TABLE = 1001

        private const val URI_ID = 1002

    }

    init {

        URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        //addUri ánh xạ đến 1 giá trị nguyên -> match() trả về giá trị số nguyên
        URI_MATCHER.addURI(AUTHORITY, "engineer", URI_TABLE)//xử lý URI bảng
        //kí tự # đã sử dụng
        URI_MATCHER.addURI(AUTHORITY, "engineer/#", URI_ID)//xử lý URI 1 hàng
    }

    private lateinit var sqLiteHelper: TableSQLiteHelper

    override fun onCreate(): Boolean {
        sqLiteHelper = TableSQLiteHelper(context!!)
        //đã khởi tạo xong provider
        return true
    }

    override fun query(
        uri: Uri,               //nội dung của bảng
        projections: Array<out String>?, //các cột trả về mỗi hàng
        selections: String?,             //tiêu chí lựa chọn
        selectionArgs: Array<out String>?,  //tiêu chí lựa chọn
        sortOrder: String?,                 //thứ tự sắp xếp cho các hàng được trả về
    ): Cursor? {
        val builder = SQLiteQueryBuilder()
        builder.tables = ENGINEER_TABLE
        if (URI_MATCHER.match(uri) == URI_ID) {
            val id = uri.pathSegments[1]          //vị trí
            builder.appendWhere("id=$id")
        }
        val db = sqLiteHelper.readableDatabase
        val cursor = builder.query(db, projections, selections, selectionArgs,
            null, null, sortOrder)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        require(URI_MATCHER.match(uri) == URI_TABLE) { "I don't know you... please try with another!" }

        //trả về đối tượng SQLite database có thể truy xuất
        val db = sqLiteHelper.writableDatabase
        val rowId = db.insert(ENGINEER_TABLE, null, values)//vị trí hàng thêm
        Log.d("doanpt", "insert result is:$rowId")
        if (rowId > 0) {

            //contentUris để làm việc riêng với id
                //trả về nội dung uri của hàng mới
            val result =
                ContentUris.withAppendedId(CONTENT_URI,
                    rowId)


            //thông báo thay đổi
            context!!.contentResolver.notifyChange(result, null)
            return result
        }
        throw IllegalArgumentException("Fail to insert with uri:$uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = sqLiteHelper.writableDatabase
        var count = 0
        when (URI_MATCHER.match(uri)) {
            URI_TABLE -> count =
                db.delete(ENGINEER_TABLE, selection, selectionArgs)
            URI_ID -> {
                val id: String = uri.pathSegments[1]
                var where: String = "$ID = '$id'"
                if (selection != null) {
                    where = "$where and $selection"
                }
                count = db.delete(ENGINEER_TABLE, where, selectionArgs)
            }
            else -> {
            }
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        val db = sqLiteHelper.writableDatabase
        var count = 0
        when (URI_MATCHER.match(uri)) {
            URI_TABLE -> count =
                db.update(ENGINEER_TABLE, values, selection, selectionArgs)
            URI_ID -> {
                //lấy phần id cuối cùng, cắt dấu gạch
                val id: String = uri.pathSegments[1]
                var where: String = "$ID = '$id'"
                if (selection != null) {
                    where = "$where and $selection"
                }
                count = db.update(ENGINEER_TABLE, values, where, selectionArgs)
            }
            else -> {
            }
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}