package com.appsrui.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.appsrui.movies.R

@Composable
fun MovieComponent(
    image: String,
    title: String,
    rating: Double?,
    navigateToDetailScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateToDetailScreen()
            }
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_placeholder),
            modifier = Modifier
                .height(300.dp)
                .clip(
                    RoundedCornerShape(14.dp),
                )
        )

        Text(
            modifier = Modifier,
            text = title,
            color = Color.White,
        )

        if (rating != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Text(text = "$rating", color = Color.White)

                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFF9E22),
                )
            }
        }
    }
}