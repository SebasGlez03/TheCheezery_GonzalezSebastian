package gonzalez.sebastian.thecheepery_gonzalezsebastian.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import gonzalez.sebastian.thecheepery_gonzalezsebastian.components.ProductForm
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.DatabaseHelper
import gonzalez.sebastian.thecheepery_gonzalezsebastian.data.ProductsDAO
import gonzalez.sebastian.thecheepery_gonzalezsebastian.domain.Product
import gonzalez.sebastian.thecheepery_gonzalezsebastian.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(navController: NavController) {
    val context = LocalContext.current

    val viewModel: ProductViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dbHelper = DatabaseHelper(context)
                val dao = ProductsDAO(dbHelper)
                return ProductViewModel(dao) as T
            }
        }
    )

    ProductForm(
        innerPadding = PaddingValues(16.dp),
        onSaveProduct = { name, price, image, description, type ->
            val newProduct = Product(
                name = name,
                price = price,
                image = if (image.isEmpty()) null else image,
                description = if (description.isEmpty()) null else description,
                type = type
            )
            viewModel.saveProduct(newProduct)
            navController.popBackStack()
        }
    )
}
