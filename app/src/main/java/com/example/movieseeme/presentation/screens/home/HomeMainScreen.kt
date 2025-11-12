import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.movieseeme.presentation.components.item_movies.ItemBotton
import com.example.movieseeme.presentation.components.item_movies.ItemMovie
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMainScreen(movies: List<String>, scrollState: LazyListState) {
//    val pagerState = rememberPagerState(pageCount = { size })
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
    val itemState = 10

    LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
        item {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 42.dp),
                pageSpacing = 5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(430.dp),
                verticalAlignment = Alignment.Top
            ) { page ->
                val realIndex = page % movies.size
                val imageUrl = movies[realIndex]


                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val scale = 1f - abs(pageOffset) * 0.2f  // thu nhỏ dần item lệch trung tâm
                val alpha = 1f - abs(pageOffset) * 0.5f   // làm mờ item lệch tâm

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .fillMaxSize()

                ) {
                    ItemMovie(
                        modifier = Modifier.fillMaxSize(),
                        myListClick = {},
                        playClick = {},
                        imageUrl = imageUrl
                    )
                }
            }
        }

        items(
            listOf(
                "Continue Watching",
                "Trailer You’ve Watched",
                "Action",
                "Anime"
            )
        ) { category ->
            RowItemMovie(value = category, moreClick = {}, itemState = itemState)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun RowItemMovie(value: String, moreClick: () -> Unit, itemState: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleHeader2
        )

        Text(
            text = "View more",
            style = MaterialTheme.typography.titleHeader2,
            modifier = Modifier.clickable { moreClick() })
    }

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        items(itemState) {
            ItemBotton(
                modifier = Modifier.size(100.dp, 150.dp),
                itemClick = {},
                imageUrl = "https://phimimg.com/upload/vod/20250712-1/e633fbc1b95a988ae58dab0e1f6cf1d6.jpg"
            )
        }
    }
}

