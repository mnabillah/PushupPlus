package com.resonatestudios.pushupplus.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.resonatestudios.pushupplus.model.HistoryItem
import java.text.DateFormat
import java.text.ParseException
import java.util.*

class DbHistory(context: Context?) {
    private val dbHelper: OpenHelper = OpenHelper(context)
    private var db: SQLiteDatabase? = null
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        db!!.close()
    }

    @get:Throws(ParseException::class)
    val all: ArrayList<HistoryItem>
        get() = getAll(columns[0])

    @Throws(ParseException::class)
    fun getAll(sortBy: String): ArrayList<HistoryItem> {
        val historyItems = ArrayList<HistoryItem>()
        val cursor: Cursor = db!!.query(TABLE_NAME, columns, null, null, null, null, "$sortBy ASC")
        if (cursor.moveToFirst()) {
            historyItems.add(HistoryItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getLong(2),
                    DateFormat.getDateInstance().parse(cursor.getString(3))!!
            ))
            while (cursor.moveToNext()) {
                historyItems.add(HistoryItem(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getLong(2),
                        DateFormat.getDateInstance().parse(cursor.getString(3))!!
                ))
            }
        }
        cursor.close()
        return historyItems
    }

    fun insert(amount: Int, duration: Long, date: Date): Boolean {
        val newValue = ContentValues()
        newValue.put("AMOUNT", amount)
        newValue.put("DURATION", duration)
        newValue.put("DATE", DateFormat.getDateInstance().format(date))
        return db!!.insert(TABLE_NAME, null, newValue) > 0
    }

    fun deleteAll(): Boolean {
        return db!!.delete(TABLE_NAME, "ID>-1", null) > 0
    }

    companion object {
        const val TABLE_NAME = "HISTORY"
        @JvmField
        val columns = arrayOf("ID", "AMOUNT", "DURATION", "DATE")
    }

}