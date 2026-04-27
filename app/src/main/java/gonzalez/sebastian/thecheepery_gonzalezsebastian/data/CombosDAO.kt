package gonzalez.sebastian.thecheepery_gonzalezsebastian.data

import android.content.ContentValues
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.CombosEntry
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.ProductsComboEntry
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.CheezeryContract.ProductsEntry
import gonzalez.sebastian.thecheepery_gonzalezsebastian.domain.Combo
import gonzalez.sebastian.thecheepery_gonzalezsebastian.domain.Product

class CombosDAO(private val dbHelper: DatabaseHelper) {

    fun insertCombo(name: String, price: Float, productIds: List<Int>): Long {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues().apply {
                put(CombosEntry.COLUMN_NAME, name)
                put(CombosEntry.COLUMN_PRICE, price)
            }
            val comboId = db.insert(CombosEntry.TABLE_NAME, null, values)

            if (comboId != -1L) {
                productIds.forEach { productId ->
                    val relValues = ContentValues().apply {
                        put(ProductsComboEntry.COLUMN_COMBO_ID, comboId)
                        put(ProductsComboEntry.COLUMN_PRODUCT_ID, productId)
                    }
                    db.insert(ProductsComboEntry.TABLE_NAME, null, relValues)
                }
                db.setTransactionSuccessful()
            }
            return comboId
        } finally {
            db.endTransaction()
        }
    }

    fun getAllCombos(): List<Combo> {
        val db = dbHelper.readableDatabase
        val combos = mutableListOf<Combo>()
        
        val cursor = db.query(CombosEntry.TABLE_NAME, null, null, null, null, null, null)
        
        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(CombosEntry.COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(CombosEntry.COLUMN_NAME))
                val price = c.getFloat(c.getColumnIndexOrThrow(CombosEntry.COLUMN_PRICE))
                
                val products = getProductsForCombo(id)
                combos.add(Combo(id, name, price, products))
            }
        }
        return combos
    }

    private fun getProductsForCombo(comboId: Int): List<Product> {
        val db = dbHelper.readableDatabase
        val products = mutableListOf<Product>()
        
        val query = """
            SELECT p.* FROM ${ProductsEntry.TABLE_NAME} p
            INNER JOIN ${ProductsComboEntry.TABLE_NAME} pc ON p.${ProductsEntry.COLUMN_ID} = pc.${ProductsComboEntry.COLUMN_PRODUCT_ID}
            WHERE pc.${ProductsComboEntry.COLUMN_COMBO_ID} = ?
        """.trimIndent()
        
        val cursor = db.rawQuery(query, arrayOf(comboId.toString()))
        
        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = c.getFloat(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = c.getString(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val desc = c.getString(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val type = c.getString(c.getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                products.add(Product(id, name, price, image, desc, type))
            }
        }
        return products
    }
}
