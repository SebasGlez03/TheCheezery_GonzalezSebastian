package gonzalez.sebastian.thecheepery_gonzalezsebastian.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.ProductsDAO
import gonzalez.sebastian.thecheepery_gonzalezsebastian.domain.Product
import kotlinx.coroutines.launch

class ProductsViewModel(private val dao: ProductsDAO, private val context: Context): ViewModel() {
    var productsListState by mutableStateOf(listOf<Product>())

    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

    fun saveProduct(product: Product) {
        val newProduct = dao.insertProduct(product)
        if (newProduct != -1L) {
            Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
            getAllProducts()
        } else {
            Toast.makeText(context, "Hubo un error al guardar", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllProducts() {
        productsListState = dao.getAllProducts()
    }
}