package com.example.coworku.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coworku.ui.profile.ProfileScreen
import com.example.coworku.ui.projects.MyProjectsScreen
import com.example.coworku.ui.projects.ProjectsListScreen
import com.example.coworku.ui.projects.SavedProjectsScreen

// Data class for Bottom Navigation items with both selected and unselected icons
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Explore : BottomNavItem("explore", "Explorar", Icons.Rounded.Search, Icons.Default.Search)
    object MyProjects : BottomNavItem("my_projects", "Mis Proyectos", Icons.Rounded.Work, Icons.Default.WorkOutline)
    object Saved : BottomNavItem("saved", "Guardados", Icons.Rounded.Favorite, Icons.Default.FavoriteBorder)
    object Profile : BottomNavItem("profile", "Perfil", Icons.Rounded.Person, Icons.Default.PersonOutline)
}

@Composable
fun MainScreen(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        MainNavigationHost(
            navController = navController,
            mainNavController = mainNavController,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Explore,
        BottomNavItem.MyProjects,
        BottomNavItem.Saved,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                label = { Text(screen.label, style = MaterialTheme.typography.labelMedium) },
                icon = {
                    // Animate between selected and unselected icons
                    AnimatedContent(
                        targetState = isSelected,
                        transitionSpec = {
                             fadeIn(animationSpec = tween(200)) + slideInVertically { it } togetherWith
                                     fadeOut(animationSpec = tween(200)) + slideOutVertically { -it }
                        }, label = "BottomNavIconAnimation"
                    ) { targetState ->
                        Icon(
                            imageVector = if (targetState) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = screen.label
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surface // No indicator color
                )
            )
        }
    }
}

@Composable
fun MainNavigationHost(
    navController: NavHostController,
    mainNavController: NavController,
    modifier: Modifier
) {
    NavHost(navController, startDestination = BottomNavItem.Explore.route, modifier = modifier) {
        composable(BottomNavItem.Explore.route) { ProjectsListScreen(navController = mainNavController) }
        composable(BottomNavItem.MyProjects.route) { MyProjectsScreen(navController = mainNavController) }
        composable(BottomNavItem.Saved.route) { SavedProjectsScreen(navController = mainNavController) }
        composable(BottomNavItem.Profile.route) { ProfileScreen(navController = mainNavController) }
    }
}
