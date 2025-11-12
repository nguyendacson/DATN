package com.example.movieseeme.presentation.screens.home

import BottomBar
import HomeMainScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieseeme.presentation.components.item_movies.OptionHome
import com.example.movieseeme.presentation.screens.new_hot.NewHotScreen
import com.example.movieseeme.presentation.screens.profile.ProfileScreen
import com.example.movieseeme.presentation.theme.extension.titleHeader

@Composable
fun HomeScreen(
    rootNavController: NavController,
//    viewModel: UserViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()

    val fakeMovies = List(10) { index ->
        "https://phimimg.com/upload/vod/20250712-1/e633fbc1b95a988ae58dab0e1f6cf1d6.jpg" // ảnh demo random
    }
    val homeNavController = rememberNavController()

    Scaffold(
        topBar = {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(WindowInsets.statusBars.asPaddingValues())
                    .padding(start = 18.dp),
                scrollState,
                search = {})
        },
        bottomBar = { BottomBar(navController = homeNavController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = "home_main"
            ) {
                composable("home_main") { HomeMainScreen(fakeMovies, scrollState) }
                composable("new_hot") { NewHotScreen() }
                composable("profile") { ProfileScreen() }
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier,
    scrollState: LazyListState,
    search: () -> Unit,
    nameUser: String = "DAC SON"
) {
    val offset by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset.coerceAtMost(100)
        }
    }
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
//                .offset { IntOffset(x = 0, y = -offset) }
                .padding(horizontal = 10.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "For $nameUser",
                style = MaterialTheme.typography.titleHeader.copy(
                    fontSize = 18.sp
                )
            )
            IconButton(onClick = search) {
                Icon(
                    Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    contentDescription = "search Movie",
                )
            }
        }

        OptionHome(modifier = Modifier.fillMaxWidth(), click = {})

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun View() {
    MaterialTheme {
        Box(modifier = Modifier.padding(top = 36.dp)) {
            HomeScreen(
                rootNavController = NavController(LocalContext.current)
            )
        }
    }
}