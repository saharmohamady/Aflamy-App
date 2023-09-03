package com.sahar.aflamy.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sahar.aflamy.R
import com.sahar.aflamy.data.model.moviedetails.MovieDetail
import com.sahar.aflamy.ui.common.ShowErrorView
import com.sahar.aflamy.ui.theme.AflamyTheme

/**
 * Display the full details of [selectedMovieId]
 */
@Composable
fun MovieDetailsScreen(selectedMovieId: String?) {

    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    selectedMovieId?.let { viewModel.getMovieDetails(it) }
    AflamyTheme {
        ScreenContent(viewModel.movieDetail) { viewModel.getLogoImagePath(it) }

        viewModel.errorState.value?.let {
            ShowErrorView(it) {
                selectedMovieId?.let { viewModel.getMovieDetails(selectedMovieId) }
            }
        }
    }
}

@Composable
private fun ScreenContent(
    movie: State<MovieDetail?>,
    getImage: (String?) -> String
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(getImage(movie.value?.posterPath))
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.value?.title ?: "",
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.body1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.value?.releaseDate ?: "",
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.caption
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.value?.overview ?: "",
                color = MaterialTheme.colors.onSecondary,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PreviewLightTheme() {
    val state =
        mutableStateOf(
            MovieDetail(
                id = 1,
                title = "Test Movie",
                releaseDate = "5/04/2022",
                overview = "test movie over view",
                posterPath = "https://image.tmdb.org/t/p/w300/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg"
            )
        )

    AflamyTheme(darkTheme = false) {
        ScreenContent(state) { url -> "" + url }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PreviewDarkTheme() {
    AflamyTheme(darkTheme = true) {
        val state = mutableStateOf(
            MovieDetail(
                id = 1,
                title = "Test Movie",
                releaseDate = "5/04/2022",
                overview = "test movie over view",
                posterPath = "https://image.tmdb.org/t/p/w300/2Icjry0xdRSNxrtsBR1F47b9r3u.jpg"
            )
        )
        ScreenContent(state) { url -> "" + url }
    }
}
