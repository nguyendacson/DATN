package com.example.movieseeme.presentation.screens.detail

import CustomToast
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.movieseeme.R
import com.example.movieseeme.domain.enum_class.detail.MovieDetailOption
import com.example.movieseeme.domain.enum_class.detail.MovieDetailTab
import com.example.movieseeme.domain.model.movie.MovieDTO
import com.example.movieseeme.presentation.components.CustomButton
import com.example.movieseeme.presentation.components.lock_screen.LockScreenOrientationPortrait
import com.example.movieseeme.presentation.components.movies.lazy.RowItemImage
import com.example.movieseeme.presentation.screens.comment.CommentScreen
import com.example.movieseeme.presentation.theme.extension.titleHeader2
import com.example.movieseeme.presentation.viewmodels.movie.CommentViewModel
import com.example.movieseeme.presentation.viewmodels.movie.InteractionViewModel
import com.example.movieseeme.presentation.viewmodels.movie.detail.DetailViewModel
import com.example.movieseeme.presentation.viewmodels.movie.profile.UserViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel,
    interactionViewModel: InteractionViewModel,
    commentViewModel: CommentViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController,
    movieId: String
) {
    LockScreenOrientationPortrait()

    val detailState by detailViewModel.detailState.collectAsState()
    val movieDetail by detailViewModel.movieDetail.collectAsState()
    val movieSame by detailViewModel.movieDetailSame.collectAsState()
    val putMovieId by detailViewModel.putMovieID.collectAsState()
    val listForYou by interactionViewModel.listMovieListForYou.collectAsState()

    val commentState by commentViewModel.commentState.collectAsState()
    val myListState by interactionViewModel.uiStateAction.collectAsState()

    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(myListState.message) {
        val msg = myListState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    LaunchedEffect(commentState.message) {
        val msg = commentState.message
        if (msg != null && msg != toastMessage) {
            toastMessage = msg
            showToast = true
        }
    }

    LaunchedEffect(movieId) {
        detailViewModel.getMovieById(movieId = movieId)
    }
    LaunchedEffect(commentState) {
        commentViewModel.getComments(movieId = movieId)
    }
//    LaunchedEffect(detailState) {
//        commentViewModel.getComments(movieId = movieId)
//    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(bottom = 15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderDetail(
                modifier = Modifier.fillMaxWidth(),
                movieDTO = movieDetail
            )

            Spacer(modifier = Modifier.height(20.dp))

            HeaderDetail1(
                modifier = Modifier.fillMaxWidth(),
                movieDTO = movieDetail
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                CustomButton(
                    modifier = Modifier
                        .height(33.dp)
                        .fillMaxWidth(),
                    value = "Xem ngay",
                    onClick = { navController.navigate("episode?movieId=${putMovieId}") },
                    icon = true,
                    isBold = false,
                    contentIcon = "play video",
                    imageVector = Icons.Default.PlayArrow,
                    isImageVector = true,
                    itemIcon = 0
                )

                CustomButton(
                    modifier = Modifier
                        .height(33.dp)
                        .fillMaxWidth(),
                    value = "Trailer",
                    onClick = {
                        val videoId = movieDetail?.trailerUrl ?: ""
                        if (videoId.isNotBlank()) {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(videoId)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                            interactionViewModel.postMovieToTrailer(movieId = movieId)
                        } else {
                            commentViewModel.setMessage(
                                message = "Không có trailer",
                                success = false
                            )
                        }
                    },
                    icon = true,
                    isBold = false,
                    contentIcon = "play video",
                    imageVector = Icons.Default.PlayArrow,
                    isImageVector = true
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    //Dowload
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
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

                        Spacer(modifier = Modifier.height(15.dp))

                        //ContentText
                        ContentDetail(
                            modifier = Modifier.fillMaxWidth(),
                            detailViewModel = detailViewModel
                        )

                        Spacer(modifier = Modifier.height(15.dp))
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
                        onClick = { detailViewModel.getMovieById(it) },
                        movies = movieSame.filter { it.id != movieId }.take(10)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    RowItemImage(
                        value = "Dành cho bạn",
                        moreClick = { },
                        isMore = false,
                        onClick = { detailViewModel.getMovieById(it) },
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
                    detailViewModel.clearMessage()
                    interactionViewModel.clearMessage()
                    commentViewModel.clearMessage()
                }
            )
        }
    }
}

@Composable
fun HeaderDetail(
    modifier: Modifier,
    movieDTO: MovieDTO?
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(265.dp)
    ) {
        val (thumbUrl, subtitle) = createRefs()
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .constrainAs(thumbUrl) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentDescription = "thurm image",
            contentScale = ContentScale.FillBounds,
            painter = rememberAsyncImagePainter(
                model = movieDTO?.thumbUrl ?: R.drawable.item_loading
            ),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .constrainAs(subtitle) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(start = 25.dp, end = 35.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(110.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        RoundedCornerShape(15.dp)
                    )
                    .clip(RoundedCornerShape(15.dp))
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        model = movieDTO?.posterUrl ?: R.drawable.item_loading
                    ),
                    contentScale = ContentScale.Crop, contentDescription = "Image poster"
                )
            }
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = movieDTO?.name ?: "Đang cập nhật",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleHeader2.copy(fontSize = 18.sp)
            )
        }

    }
}

@Composable
fun HeaderDetail1(
    modifier: Modifier,
    movieDTO: MovieDTO?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            10.dp,
            Alignment.CenterHorizontally
        )
    ) {
        val colorUse = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        val textStyle = MaterialTheme.typography.titleHeader2.copy(
            color = colorUse,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = "calender", tint = colorUse
            )
            Text(
                text = movieDTO?.year ?: "Đang cập nhật",
                style = textStyle
            )
        }

        VerticalDivider(
            color = colorUse,
            thickness = 2.dp,
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                Icons.Default.AccessTime,
                contentDescription = "calender", tint = colorUse
            )
            Text(
                text = movieDTO?.time ?: "Đang cập nhật",
                style = textStyle
            )
        }

        VerticalDivider(
            color = colorUse,
            thickness = 2.dp,
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                Icons.Default.Movie,
                contentDescription = "calender", tint = colorUse
            )
            Text(
                text = if (movieDTO?.status == "completed") {
                    movieDTO.episodeCurrent
                } else {
                    "${movieDTO?.episodeCurrent}/${movieDTO?.episodeTotal}"
                },
                style = textStyle
            )
        }
    }
}

@Composable
fun RowOptionDetail(
    modifier: Modifier,
    onClickOption: (MovieDetailOption) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            28.dp,
            Alignment.CenterHorizontally
        )
    ) {
        MovieDetailOption.entries.forEach { option ->
            ItemOnDetailOption(
                data = option,
                onClick = { onClickOption(option) })
        }
    }
}


@Composable
fun ContentDetail(
    modifier: Modifier,
    detailViewModel: DetailViewModel
) {
    val currentSelectedTab by detailViewModel.selectedContent.collectAsState()
    val content by detailViewModel.contentForCurrentTab.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RowOptionContent(
            modifier = Modifier.fillMaxWidth(),
            currentSelectedTab = currentSelectedTab,
            onSelected = detailViewModel::onOptionContent
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExpandableText(
            modifier = Modifier.fillMaxWidth(),
            text = content,
            collapsedMaxLines = 3,
            style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal),
            detailViewModel = detailViewModel
        )
    }
}

@Composable
fun RowOptionContent(
    modifier: Modifier,
    currentSelectedTab: MovieDetailTab,
    onSelected: (MovieDetailTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            30.dp,
            Alignment.CenterHorizontally
        )
    ) {
        MovieDetailTab.entries.forEach { tab ->
            ItemOnTab(
                tab = tab,
                isSelected = currentSelectedTab == tab,
                onClick = { onSelected(tab) })
        }
    }
}


@Composable
fun ItemOnDetailOption(
    data: MovieDetailOption,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterVertically)
    ) {
        Icon(
            data.icon,
            contentDescription = "iocn",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = data.title,
            style = MaterialTheme.typography.titleHeader2.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }

}

@Composable
fun ItemOnTab(
    tab: MovieDetailTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(65.dp)
            .clickable { onClick() },

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
    ) {

        if (isSelected) {
            Text(text = tab.title, style = MaterialTheme.typography.titleHeader2, maxLines = 1)

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 3.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        } else {
            Text(
                text = tab.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleHeader2.copy(fontWeight = FontWeight.Normal)
            )

            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun ExpandableText(
    modifier: Modifier,
    text: String,
    collapsedMaxLines: Int = 3,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current,
    detailViewModel: DetailViewModel
) {
    val isExpanded = detailViewModel.isExpanded
    val maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines
    val overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis

    val textModifier = modifier.clickable {
        detailViewModel.toggleExpand()
    }
    Text(
        modifier = textModifier.fillMaxWidth(),
        text = text.ifBlank { "Đang cập nhật" },
        overflow = overflow,
        maxLines = maxLines,
        style = style
    )

    if (!isExpanded) {
        Text(
            text = "Xem thêm",
            modifier = Modifier
                .clickable { detailViewModel.toggleExpand() }
                .padding(top = 2.dp),
            style = MaterialTheme.typography.titleHeader2
        )
    }

    if (isExpanded) {
        Text(
            text = "Thu gọn",
            modifier = Modifier
                .clickable { detailViewModel.toggleExpand() }
                .padding(top = 2.dp),
            style = MaterialTheme.typography.titleHeader2
        )
    }
}
