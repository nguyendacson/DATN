import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieseeme.MainActivity
import com.example.movieseeme.domain.enum_class.detail.MovieDetailOption
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.lock_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.components.lock_screen.UnlockScreenOrientationSensor
import com.example.movieseeme.presentation.components.movies.lazy.RowItemImage
import com.example.movieseeme.presentation.screens.comment.CommentScreen
import com.example.movieseeme.presentation.screens.detail.ExpandableText
import com.example.movieseeme.presentation.screens.detail.RowOptionDetail
import com.example.movieseeme.presentation.screens.episode.HlsVideoPlayer
import com.example.movieseeme.presentation.theme.extension.orange
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.CommentViewModel
import com.example.movieseeme.presentation.viewmodels.movie.EpisodeViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.detail.DetailViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@Composable
fun EpisodeScreen(
    movieId: String,
    episodeId: String,
    episodeViewModel: EpisodeViewModel,
    interactionViewModel: InteractionViewModel,
    commentViewModel: CommentViewModel,
    userViewModel: UserViewModel,
    detailViewModel: DetailViewModel,
    navController: NavController
) {
    UnlockScreenOrientationSensor()

    LaunchedEffect(movieId) {
        if (episodeId.isNotBlank()) {
            detailViewModel.getMovieById(movieId)
        }
        episodeViewModel.getEpisodes(movieId, episodeId)
    }

    val episodeList by episodeViewModel.episode.collectAsState()
    val selectedDataMovie by episodeViewModel.selectedDataMovie.collectAsState()
    val episodeState by episodeViewModel.episodeState.collectAsState()

    val movieDetail by detailViewModel.movieDetail.collectAsState()

    val movieSame by detailViewModel.movieDetailSame.collectAsState()

    val listForYou by interactionViewModel.listMovieListForYou.collectAsState()

    val commentState by commentViewModel.commentState.collectAsState()
    val myListState by interactionViewModel.uiStateAction.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(commentState.message) {
        val msg = commentState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    LaunchedEffect(myListState.message) {
        val msg = myListState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    LaunchedEffect(commentState) {
        commentViewModel.getComments(movieId = movieId)
    }

    val context = LocalContext.current
    val activity = context as? MainActivity
    val isPip = activity!!.isInPictureInPictureMode
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    DisposableEffect(Unit) {
        activity.setPipEnabled(true)
        onDispose {
            activity.setPipEnabled(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(if (isPip) WindowInsets(0).asPaddingValues() else WindowInsets.statusBars.asPaddingValues())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .then(
                    if (isLandscape) Modifier.fillMaxSize()
                    else Modifier.wrapContentHeight()
                )
        ) {
            HlsVideoPlayer(
                episodeViewModel = episodeViewModel,
                movieID = movieId,
                interactionViewModel = interactionViewModel,
                modifier = Modifier.fillMaxWidth()
            )

        }
        if (!isPip) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(550.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomButton(
                        modifier = Modifier
                            .height(33.dp)
                            .fillMaxWidth(),
                        value = "Thu nhỏ",
                        onClick = {
                            val params = PictureInPictureParams.Builder()
                                // Tùy chọn: Set aspect ratio cho cửa sổ PiP khớp với video 16:9
                                // .setAspectRatio(Rational(16, 9))
                                .build()
                            activity.enterPictureInPictureMode(params)
                        },
                        icon = false,
                        isBold = false,
                        contentIcon = "pip video",
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    RowOptionDetail(
                        modifier = Modifier.fillMaxWidth(),
                        onClickOption = { option ->
                            when (option) {
                                MovieDetailOption.LIKE -> {
                                    interactionViewModel.postMovieToLike(movieId = movieId)
                                }

                                MovieDetailOption.SHARE -> {}
                                MovieDetailOption.ADD -> {
                                    interactionViewModel.postMovieToMyList(movieId = movieId)
                                }

                                MovieDetailOption.DOWNLOAD -> {}
                            }
                        }
                    )
                }
                items(episodeList) { episode ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        EpisodeItemGrid(
                            episode = episode,
                            onClick = { dataMovie ->
                                episodeViewModel.selectEpisode(dataMovie)
                            },
                            selectedDataMovie = selectedDataMovie,
                            columns = 4
                        )
                    }

                }
                item {
                    Spacer(modifier = Modifier.height(15.dp))

                    val textView: String =
                        if (movieDetail!!.status == "completed") movieDetail!!.episodeCurrent else (movieDetail!!.episodeCurrent + "/" + movieDetail!!.episodeTotal)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 11.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        Text(
                            text = movieDetail!!.originName + "-" + textView,
                            style = MaterialTheme.typography.titleHeader2.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.orange
                            )
                        )

                        Text(
                            text = movieDetail!!.name + "-" + textView,
                            style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal)
                        )

                        ExpandableText(
                            modifier = Modifier.fillMaxWidth(),
                            text = movieDetail?.content ?: "Đang cập nhật",
                            collapsedMaxLines = 3,
                            style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
                            detailViewModel = detailViewModel
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        CommentScreen(
                            modifier = Modifier.fillMaxWidth(),
                            movieId = movieId,
                            commentViewModel = commentViewModel,
                            userViewModel = userViewModel
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    RowItemImage(
                        value = "Phim liên quan",
                        moreClick = { },
                        isMore = false,
                        onClick = {
                            navController.navigate("detailScreen/${it}")
                        },
                        movies = movieSame.filter { it.id != movieId }.take(10)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    RowItemImage(
                        value = "Dành cho bạn",
                        moreClick = { },
                        isMore = false,
                        onClick = {
                            navController.navigate("detailScreen/${it}")
                        },
                        movies = listForYou.filter { it.id != movieId }.take(10)
                    )
                }
            }
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
                    episodeViewModel.clearMessage()
                    interactionViewModel.clearMessage()
                    commentViewModel.clearMessage()
                }
            )
        }
    }
}
