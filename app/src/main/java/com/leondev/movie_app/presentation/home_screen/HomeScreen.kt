package com.leondev.movie_app.presentation.home_screen

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leondev.movie_app.presentation.explore_screen.ExploreScreen
import com.leondev.movie_app.presentation.movie_list_screen.MovieListScreen
import com.leondev.movie_app.presentation.movie_list_screen.state.MovieListUIEvent
import com.leondev.movie_app.presentation.viewmodel.MovieViewModel
import com.leondev.movie_app.util.route.Screen
import com.leondev.movie_app.util.ui.theme.MovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val movieListViewModel = hiltViewModel<MovieViewModel>()
    val movieListUIState by movieListViewModel.movieListState.collectAsState()
    val bottomNavigationBarController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavigationBarController,
                onEvent = movieListViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieListUIState.currentPage == Screen.MovieList.route) {
                            Screen.Home.route
                        } else {
                            Screen.Explore.route
                        }
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = bottomNavigationBarController,
                startDestination = Screen.MovieList.route
            ) {

                composable(Screen.MovieList.route) {
                    MovieListScreen(movieListUIState, navController, onEvent = {

                    })
                }

                composable(Screen.Explore.route) {
                    ExploreScreen(movieListUIState, navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(controller: NavHostController, onEvent: (MovieListUIEvent) -> Unit) {
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
                    selected = selected.value == index,
                    onClick = {
                        selected.value = index
                        when (selected.value) {
                            0 -> {
                                onEvent(MovieListUIEvent.Navigate(Screen.MovieList.route))
                                controller.popBackStack()
                                controller.navigate(Screen.MovieList.route)
                            }

                            1 -> {
                                onEvent(MovieListUIEvent.Navigate(Screen.Explore.route))
                                controller.popBackStack()
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MovieAppTheme {

    }
}