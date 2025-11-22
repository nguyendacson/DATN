
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.movies.item.ItemOnPager

@Composable
fun ItemHomeMovie(
    modifier: Modifier,
    movie: MovieDTO,
    playClick: (String) -> Unit,
    myListClick: (String) -> Unit
) {
    Box(
        modifier = modifier
//            .background(Color.LightGray)
            .size(290.dp, 410.dp)
            .clip(RoundedCornerShape(9.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.name,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ItemOnPager(
                    modifier = Modifier.size(
                        110.dp,
                        33.dp
                    ),
                    value = "Xem",
                    isPlay = true,
                    itemIcon = Icons.Default.PlayArrow,
                    contentIcon = "xem",
                    shape = 10,
                    onClick = { playClick(movie.id) },
                )

                ItemOnPager(
                    modifier = Modifier.size(
                        110.dp,
                        33.dp
                    ),
                    value = "Yêu Thích",
                    isPlay = false,
                    itemIcon = Icons.Default.Add,
                    contentIcon = "yêu thích",
                    shape = 10,
                    onClick = { myListClick(movie.id) },
                )
            }
        }
    }
}