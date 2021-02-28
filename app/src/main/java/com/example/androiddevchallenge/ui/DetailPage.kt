package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.PuppyDataRepo
import com.example.androiddevchallenge.ui.ext.percentOffsetX
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun DetailPage() {
    val mainViewModel = viewModel<MainViewModel>()
    val showingDetail = mainViewModel.showingDetail.observeAsState().value ?: false
    val puppy = mainViewModel.detailPuppy.value

    val percentOffset = animateFloatAsState(targetValue = if (showingDetail) 0f else 1f)

    DetailContent(puppy = puppy, percentOffset = percentOffset.value)

}

@Composable
fun DetailContent(puppy: Puppy?, percentOffset: Float) {

    Column(
        Modifier
            .percentOffsetX(percentOffset)
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        if (puppy != null) {

            Spacer(modifier = Modifier.height(40.dp))
            Text(text = puppy.name, fontSize = 36.sp)
            Spacer(modifier = Modifier.height(10.dp))

            val pageState = remember {
                PagerState().apply {
                    minPage = 0
                    maxPage = (puppy.images.size - 1).coerceAtLeast(0)
                }
            }
            Pager(state = pageState, modifier = Modifier.height(350.dp)) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 18.dp, end = 18.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxSize()
                        .shadow(20.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = puppy.images[page]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            val currentPage = pageState.currentPage
            val maxPage = pageState.maxPage
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${currentPage + 1}/${maxPage + 1}")
            }

            Column(
                Modifier
                    .weight(1f)
                    .height(0.dp)
                    .padding(start = 20.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "My name is ${puppy.name}!",
                        style = typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(1.dp))

                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically),
                        colorFilter = ColorFilter.tint(puppy.sex.color),
                        imageVector = puppy.sex.label, contentDescription = null
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(modifier = Modifier.padding(top = 3.dp)) { ProfileItem("Age", puppy.age) }
                    Row(modifier = Modifier.padding(top = 3.dp)) {
                        ProfileItem(
                            "Breed",
                            puppy.breed
                        )
                    }
                    Row(modifier = Modifier.padding(top = 3.dp)) {
                        ProfileItem(
                            "Color",
                            puppy.color
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = puppy.story)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50)),
                onClick = {}) {
                Text(text = "Take me home ❤️")
            }
            Spacer(modifier = Modifier.height(20.dp))
        } else {
            Spacer(modifier = Modifier.fillMaxSize())
        }
    }

}

@Composable
fun ProfileItem(key: String, value: String) {
    Text(
        key, modifier = Modifier.width(70.dp),
        fontWeight = FontWeight.Bold
    )
    Text(value, fontWeight = FontWeight.Light)
}

