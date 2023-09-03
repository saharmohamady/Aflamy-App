package com.sahar.aflamy.presentation.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sahar.aflamy.R
import com.sahar.aflamy.data.model.movieslist.MoviesListItem
import com.sahar.aflamy.presentation.router.Screen
import com.sahar.aflamy.ui.theme.AflamyTheme

/**
 * Display list of Movies
 */
@Composable
fun MoviesListScreen(navController: NavController) {

    val viewModel = hiltViewModel<MoviesListViewModel>()
    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()

    AflamyTheme {
        LazyColumn {
            items(
                count = movies.itemCount,
                key = null
            ) { index ->
                MoviesListScreenContent(
                    viewModel.getLogoImagePath(movies[index]?.posterPath),
                    movies[index]
                ) {
                    onListItemClicked(navController, movies[index]?.id.toString())
                }
            }

        }
        handleLoadingUI(movies, viewModel)
    }


}

/**
 * handle Loading Ui on first Load and on load more
 */
@Composable
private fun handleLoadingUI(
    movies: LazyPagingItems<MoviesListItem>,
    viewModel: MoviesListViewModel
) {

    when (movies.loadState.refresh) {
        is LoadState.Error -> {
            (movies.loadState.refresh as LoadState.Error).error.message?.let {
                ShowToastError(it) { viewModel.invalidate() }
            }
        }
        is LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.intial_load)
                )

                CircularProgressIndicator(color = MaterialTheme.colors.onSecondary)
            }

        }
        else -> {}
    }
    when (movies.loadState.append) {
        is LoadState.Error -> {
            (movies.loadState.refresh as LoadState.Error).error.message?.let {
                ShowToastError(it) { viewModel.invalidate() }
            }
        }
        is LoadState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text(text = stringResource(R.string.load_more))
                CircularProgressIndicator(color = MaterialTheme.colors.onSecondary)
            }
        }
        else -> {}
    }
}

@Composable
fun ShowToastError(message: String, reLoad: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(text = stringResource(R.string.no_network))
        Button(onClick = { reLoad }) {
            Text(text = stringResource(R.string.try_again))
        }
    }
    Toast.makeText(
        LocalContext.current, message,
        Toast.LENGTH_SHORT
    ).show()
}

fun onListItemClicked(navController: NavController, movieId: String?) {
    movieId?.let { navController.navigate(Screen.MoviesDetailsScreen.withArgs(movieId)) }
}

@Composable
private fun MoviesListScreenContent(
    url: String?,
    movie: MoviesListItem?,
    onClick: (Int?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onSecondary)
            .padding(5.dp)

    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colors.onPrimary)
                .padding(5.dp)
                .clickable {
                    onClick.invoke(movie?.id)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie?.title ?: "",
                    color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie?.releaseDate ?: "",
                    color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Divider()
    }
}
