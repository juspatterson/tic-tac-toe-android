package app.emojitictactow.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "HighScoreDatabase"
        private val TABLE_CONTACTS = "HighScoreTable"
        private val KEY_NAME = "name"
        private val KEY_NUMBEROFMOVES = "numberOfMoves"
        private val KEY_DATEANDTIME = "dateAndTime"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                 + KEY_NAME + " TEXT," + KEY_NUMBEROFMOVES + " INTEGER,"
                + KEY_DATEANDTIME + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addHighScore(highScore: HighScoreModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, highScore.name)
        contentValues.put(KEY_NUMBEROFMOVES, highScore.numberOfMoves)
        contentValues.put(KEY_DATEANDTIME,highScore.dateAndTime )
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewHighScores():List<HighScoreModelClass>{
        val highScoreList:ArrayList<HighScoreModelClass> = ArrayList<HighScoreModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var numberOfMoves: Int
        var name: String
        var dateAndTime: String
        if (cursor.moveToFirst()) {
            do {
                numberOfMoves = cursor.getInt(cursor.getColumnIndex("numberOfMoves"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                dateAndTime = cursor.getString(cursor.getColumnIndex("dateAndTime"))
                val highScore= HighScoreModelClass(name = name,numberOfMoves = numberOfMoves,dateAndTime = dateAndTime)
                highScoreList.add(highScore)
            } while (cursor.moveToNext())
        }
        return highScoreList
    }
}