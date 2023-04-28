package uz.coderodilov.sqldatabase.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.coderodilov.sqldatabase.model.User

const val DATABASE_NAME = "APP_DB"
const val TABLE_NAME = "users_table"
const val COL_ID = "id"
const val COL_NAME = "name"
const val COl_LAST_NAME = "last_name"
const val COL_IMAGE = "image"
const val VERSION = 1


class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME TEXT, $COl_LAST_NAME TEXT, $COL_IMAGE VARCHAR(256))"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }


    fun insertUser(name:String, lastName:String, image:ByteArray){
        val db = this.writableDatabase
        val query = "INSERT INTO $TABLE_NAME VALUES(NULL, ?, ?, ?)"

        val statement = db.compileStatement(query)
        statement.clearBindings()

        statement.bindString(1, name)
        statement.bindString(2, lastName)
        statement.bindBlob(3, image)

        statement.executeInsert()
    }


    fun readAllUser():ArrayList<User>{
        val list:ArrayList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC"

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToNext()){
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val lastName = cursor.getString(2)
                val image = cursor.getBlob(3)

                val user = User(id, name, lastName, image)
                list.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

}