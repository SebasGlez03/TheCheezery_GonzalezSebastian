package gonzalez.sebastian.thecheepery_gonzalezsebastian.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.ProductsEntry
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.CombosEntry
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.ProductsComboEntry

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cheezery.db"
        private const val DATABASE_VERSION = 3
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON")

        db.execSQL(
            """
                CREATE TABLE ${ProductsEntry.TABLE_NAME} (
                ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
                ${ProductsEntry.COLUMN_IMAGE} TEXT,
                ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
                ${ProductsEntry.COLUMN_DESCRIPTION} TEXT,
                ${ProductsEntry.COLUMN_TYPE} TEXT NOT NULL
                )
            """.trimIndent()
        )

        db.execSQL(
            """
                CREATE TABLE ${CombosEntry.TABLE_NAME} (
                ${CombosEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${CombosEntry.COLUMN_NAME} TEXT NOT NULL,
                ${CombosEntry.COLUMN_PRICE} REAL NOT NULL
                )
            """.trimIndent()
        )

        db.execSQL(
            """
                CREATE TABLE ${ProductsComboEntry.TABLE_NAME} (
                ${ProductsComboEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${ProductsComboEntry.COLUMN_COMBO_ID} INTEGER NOT NULL,
                ${ProductsComboEntry.COLUMN_PRODUCT_ID} INTEGER NOT NULL,
                FOREIGN KEY(${ProductsComboEntry.COLUMN_COMBO_ID}) REFERENCES ${CombosEntry.TABLE_NAME}(${CombosEntry.COLUMN_ID}) ON DELETE CASCADE,
                FOREIGN KEY(${ProductsComboEntry.COLUMN_PRODUCT_ID}) REFERENCES ${ProductsEntry.TABLE_NAME}(${ProductsEntry.COLUMN_ID}) ON DELETE CASCADE
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductsComboEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CombosEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
        onCreate(db)
    }
}
