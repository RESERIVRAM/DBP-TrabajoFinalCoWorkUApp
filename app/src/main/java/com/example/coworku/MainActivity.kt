package com.example.coworku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coworku.ui.auth.LoginScreen
import com.example.coworku.ui.auth.RegisterScreen
import com.example.coworku.ui.home.MainScreen
import com.example.coworku.ui.landing.LandingScreen
import com.example.coworku.ui.projects.CreateProjectScreen
import com.example.coworku.ui.projects.JoinProjectScreen
import com.example.coworku.ui.projects.JoinProjectSuccessScreen
import com.example.coworku.ui.projects.ProjectDetailScreen
import com.example.coworku.ui.theme.CoWorkUTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoWorkUTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") { LandingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { MainScreen(navController) }
        composable("projectDetail/{projectId}") { 
            ProjectDetailScreen(navController)
        }
        composable("joinProject") { JoinProjectScreen(navController) }
        composable("joinProjectSuccess") { JoinProjectSuccessScreen(navController) }
        composable("createProject") { CreateProjectScreen(navController) }
    }
}
