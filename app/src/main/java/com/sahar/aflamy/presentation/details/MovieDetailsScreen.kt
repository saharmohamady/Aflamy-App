package com.sahar.aflamy.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
