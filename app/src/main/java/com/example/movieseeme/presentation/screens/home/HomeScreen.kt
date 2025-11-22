import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieseeme.domain.model.enum.HomeTitleFull
import com.example.movieseeme.presentation.components.LoadingBounce
import com.example.movieseeme.presentation.components.movies.lazy.HomeBannerPager
import com.example.movieseeme.presentation.components.movies.lazy.HomeOption
import com.example.movieseeme.presentation.components.movies.lazy.RowItemImage
import com.example.movieseeme.presentation.screens.HeaderScreen
import com.example.movieseeme.presentation.screens.setting_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.viewmodels.movie.HomeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    interactionViewModel: InteractionViewModel,
    navController: NavController
) {
    LockScreenOrientationPortrait()
    val movieState by homeViewModel.uiState.collectAsState()
    val myListState by interactionViewModel.uiStateAction.collectAsState()


    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(myListState.message) {
        val msg = myListState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                search = { navController.navigate("search") })

            HomeOption(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                homeViewModel = homeViewModel
            )
            Spacer(modifier = Modifier.height(8.dp))

            val movies by homeViewModel.listMovieHomeFilter.collectAsState()
            val movieAction by homeViewModel.listMovieAction.collectAsState()
            val movieAnime by homeViewModel.listMovieAnime.collectAsState()
            val movieAnique by homeViewModel.listMovieAntique.collectAsState()
            val movieFantasy by homeViewModel.listMovieFantasy.collectAsState()
            val movieHistory by homeViewModel.listMovieHistory.collectAsState()
            val scrollState = rememberLazyListState()
            val pagerState = rememberPagerState(
                initialPage = Int.MAX_VALUE / 2,
                pageCount = { Int.MAX_VALUE }
            )

            if (movies.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        HomeBannerPager(
                            pagerState = pagerState,
                            movies = movies.take(10),
                            clickMyList = { id ->
                                interactionViewModel.postMovieToMyList(id)
                            },
                            clickPlay = {}
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(HomeTitleFull.entries) { category ->
                        val movieList = when (category) {
                            HomeTitleFull.ACTION -> movieAction
                            HomeTitleFull.CARTOON -> movieAnime
                            HomeTitleFull.ANTIQUE -> movieAnique
                            HomeTitleFull.FANTASY -> movieFantasy
                            HomeTitleFull.HISTORY -> movieHistory
                        }
                        RowItemImage(
                            value = category.nameTitle,
                            moreClick = { navController.navigate("fullMovie/${category.slug}") },
                            movies = movieList.take(10)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            if (movieState.isLoading) {
                LoadingBounce()
            }
        }

        if (showToast && toastMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CustomToast(
                    value = toastMessage,
                    onDismiss = {
                        showToast = false
                        interactionViewModel.clearMessage() // bây giờ mới reset
                    }
                )
            }
        }
    }
}
