package com.example.coworku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coworku.ui.auth.AuthViewModel
import com.example.coworku.ui.auth.LoginScreen
import com.example.coworku.ui.auth.RegisterScreen
import com.example.coworku.ui.community.CreateThreadScreen
import com.example.coworku.ui.community.NewsDetailScreen
import com.example.coworku.ui.community.ThreadDetailScreen
import com.example.coworku.ui.community.ThreadListScreen
import com.example.coworku.ui.faq.FaqScreen
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
    val authViewModel: AuthViewModel = viewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(navController = navController, startDestination = if (currentUser == null) "landing" else "home" ) {
        composable("landing") { LandingScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController) }
        composable("home") {
            if (currentUser == null) {
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            } else {
                MainScreen(navController, authViewModel)
            }
        }
        composable("projectDetail/{projectId}") { 
            ProjectDetailScreen(navController)
        }
        composable("joinProject") { JoinProjectScreen(navController) }
        composable("joinProjectSuccess") { JoinProjectSuccessScreen(navController) }
        composable("createProject") { CreateProjectScreen(navController) }

        // Community Routes
        composable("threadList/{categoryId}") { backStackEntry ->
            ThreadListScreen(
                categoryId = backStackEntry.arguments?.getString("categoryId") ?: "",
                onBackClick = { navController.popBackStack() },
                onThreadClick = { threadId -> navController.navigate("threadDetail/$threadId") },
                onCreateThreadClick = { navController.navigate("createThread") }
            )
        }
        composable("threadDetail/{threadId}") { backStackEntry ->
            ThreadDetailScreen(
                threadId = backStackEntry.arguments?.getString("threadId") ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("createThread") {
            CreateThreadScreen(
                onBackClick = { navController.popBackStack() },
                onThreadCreated = { threadId ->
                    navController.navigate("threadDetail/$threadId") {
                        popUpTo("home")
                    }
                }
            )
        }
        composable("newsDetail/{newsId}") { backStackEntry ->
            NewsDetailScreen(
                newsId = backStackEntry.arguments?.getString("newsId") ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }

        // FAQ Route
        composable("faq") {
            FaqScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
