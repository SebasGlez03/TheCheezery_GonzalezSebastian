package gonzalez.sebastian.thecheepery_gonzalezsebastian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import gonzalez.sebastian.thecheepery_gonzalezsebastian.screens.*
import gonzalez.sebastian.thecheepery_gonzalezsebastian.ui.theme.TheCheepery_GonzalezSebastianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheCheepery_GonzalezSebastianTheme {
                val navController = rememberNavController()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "welcome",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("menu") { MenuScreen(navController) }
                        composable("add_product") { AddProductScreen(navController) }
                        composable("add_combo") { AddComboScreen(navController) }
                        composable("combos_list") { CombosScreen() }
                        composable(
                            route = "products/{type}",
                            arguments = listOf(navArgument("type") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val type = backStackEntry.arguments?.getString("type") ?: ""
                            ProductsScreen(type)
                        }
                    }
                }
            }
        }
    }
}
