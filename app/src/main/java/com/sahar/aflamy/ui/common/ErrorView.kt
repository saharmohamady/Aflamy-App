package com.sahar.aflamy.ui.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.sahar.aflamy.R

@Composable
fun ShowErrorView(message: String, reLoad: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(text = stringResource(R.string.no_network) + " " + message)
        Button(onClick = { reLoad.invoke() }) {
            Text(text = stringResource(R.string.try_again))
        }
    }
    Toast.makeText(
        LocalContext.current, message,
        Toast.LENGTH_SHORT
    ).show()
}
