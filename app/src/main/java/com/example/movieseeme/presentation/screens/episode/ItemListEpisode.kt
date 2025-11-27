import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movieseeme.domain.model.movie.DataMovie
import com.example.movieseeme.domain.model.movie.Episode
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.sameBlack
import com.example.movieseeme.presentation.theme.extension.titleHeader2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeItemGrid(
    episode: Episode,
    onClick: (DataMovie) -> Unit,
    columns: Int,
    selectedDataMovie: DataMovie? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = episode.serverName,
            style = MaterialTheme.typography.titleHeader2.copy(color = MaterialTheme.colorScheme.orange),
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 200.dp)
                .padding(4.dp),
            contentPadding = PaddingValues(4.dp),
            userScrollEnabled = true
        ) {
            items(episode.dataMovie) { movie ->
                val isChosen = movie.id == selectedDataMovie?.id
                ItemListEpisodeIcon(
                    isChose = isChosen,
                    dataMovie = movie,
                    onClick = { onClick(it) },
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}


@Composable
fun ItemListEpisodeIcon(
    isChose: Boolean,
    dataMovie: DataMovie,
    onClick: (DataMovie) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (isChose) MaterialTheme.colorScheme.orange else MaterialTheme.colorScheme.sameBlack,
                shape = RoundedCornerShape(8.dp)
            )
            .size(50.dp, 35.dp)
            .clickable { onClick(dataMovie) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dataMovie.name,
            style = MaterialTheme.typography.titleHeader2.copy(color = Color.White)
        )
    }
}
