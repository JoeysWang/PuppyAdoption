package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R

@Composable
fun HomePage() {
    val mainViewModel = viewModel<MainViewModel>()
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(R.drawable.dog_home), contentDescription = "logo")
            Text(text = "Adopt me", fontSize = 36.sp)
        }
        OutlinedTextField(
            value = mainViewModel.searchText.observeAsState().value.orEmpty(),
            onValueChange = {
                mainViewModel.searchText.value = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Row() {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_search_24),
                        contentDescription = "search icon",
                        Modifier.padding(end = 4.dp)
                    )
                    Text(text = "search...")
                }
            }
        )
        Spacer(Modifier
                .fillMaxWidth()
                .height(10.dp))
        PuppyList()
    }

}

@Composable
fun PuppyList() {
    val mainViewModel = viewModel<MainViewModel>()
    val list = mainViewModel.puppyList.observeAsState().value.orEmpty()
    LazyColumn(Modifier.fillMaxHeight()) {
        itemsIndexed(list) { index, item ->
            PuppyItem(index, item)
        }
    }
}
