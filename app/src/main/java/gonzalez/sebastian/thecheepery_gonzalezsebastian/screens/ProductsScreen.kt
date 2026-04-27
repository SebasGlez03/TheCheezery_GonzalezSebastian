package gonzalez.sebastian.thecheepery_gonzalezsebastian.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gonzalez.sebastian.thecheepery_gonzalezsebastian.R
import gonzalez.sebastian.thecheepery_gonzalezsebastian.components.ProductForm
import gonzalez.sebastian.thecheepery_gonzalezsebastian.domain.Product
import gonzalez.sebastian.thecheepery_gonzalezsebastian.ui.theme.Pinky
import gonzalez.sebastian.thecheepery_gonzalezsebastian.viewmodel.ProductViewModel

@Composable
fun ShowProducts(viewModel: ProductViewModel) {
    Column{
        Text("Products")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(viewModel.productsListState) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Image(painterResource(R.drawable.muffin), contentDescription = "muffin")
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("${product.name}")
            Text("${product.description}")
        }
    }
}

@Composable
fun AddProductScreen(innerPadding: PaddingValues, products: List<Product>, onSaveProduct: (name: String, price: Float, image: String, description: String) -> Unit){
    var name by remember { mutableStateOf("") }
    var priceField by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
        Text("Add a new product", color = Pinky, fontSize = 30.sp, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(30.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text("Name")}
        )
        OutlinedTextField(
            value = priceField,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {priceField = it},
            label = {Text("Price")},
            trailingIcon = {Image(
                painter = painterResource(R.drawable.dolar),
                contentDescription = "Dolar icon"
            )}
        )
        OutlinedTextField(
            value = description,
            onValueChange = {description = it},
            label = {Text("Description")}
        )
    }
}


@Preview (showBackground = true)
@Composable
fun AddProductScreenPreview(){
    AddProductScreen(PaddingValues(20.dp),
        listOf(Product(1,
        "Latte",
        50f,
        "",
        "")),
        {name, price, image, description -> }
    )
}