package com.leondev.movie_app.feature.home_screen.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leondev.movie_app.feature.explore_screen.presentation.ExploreScreen
import com.leondev.movie_app.feature.explore_screen.presentation.viewmodel.ExploreViewModel
import com.leondev.movie_app.feature.home_screen.presentation.viewmodel.HomeScreenUIEvent
import com.leondev.movie_app.feature.home_screen.presentation.viewmodel.HomeViewModel
import com.leondev.movie_app.feature.movie_list.presentation.MovieListScreen
import com.leondev.movie_app.feature.movie_list.presentation.viewmodel.MovieViewModel
import com.leondev.movie_app.util.route.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val homeScreenViewModel = hiltViewModel<HomeViewModel>()
    val homeScreenUIState by homeScreenViewModel.homeScreenUIState.collectAsState()
    val bottomNavigationBarController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavigationBarController,
                onEvent = homeScreenViewModel::onEvent
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(0.dp)
        ) {
            NavHost(
                navController = bottomNavigationBarController,
                startDestination = Screen.MovieList.route
            ) {

                composable(Screen.MovieList.route) {
                    val movieListViewModel = hiltViewModel<MovieViewModel>()
                    val movieListUIState by movieListViewModel.movieListState.collectAsState()
                    MovieListScreen(movieListUIState, navController, onEvent = {

                    })
                }

                composable(Screen.Explore.route) {
                    val exploreViewModel = hiltViewModel<ExploreViewModel>()
                    val exploreUIState by exploreViewModel.exploreUIState.collectAsState()
                    ExploreScreen(exploreUIState, navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(controller: NavHostController, onEvent: (HomeScreenUIEvent) -> Unit) {
    val items = listOf(
        BottomBarItem(
            name = "Home",
            icon = Icons.Rounded.Home
        ),
        BottomBarItem(
            name = "Explore",
            icon = Icons.Rounded.Explore
        )
    )

    val selected = rememberSaveable() {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomBarItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.value = index
                        when (selected.value) {
                            0 -> {
                                onEvent(HomeScreenUIEvent.Navigate(Screen.MovieList.route))
                                controller.navigate(Screen.MovieList.route)
                            }

                            1 -> {
                                onEvent(HomeScreenUIEvent.Navigate(Screen.Explore.route))
                                controller.navigate(Screen.Explore.route)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomBarItem.icon,
                            contentDescription = bottomBarItem.name,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomBarItem.name,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomBarItem(
    val name: String,
    val icon: ImageVector
)
