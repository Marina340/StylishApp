package com.example.stylish
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle


@Composable
fun OnBoardItem(page: OnBoardModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .height(350.dp)
                .width(350.dp)
                .padding(bottom = 20.dp)
        )
        Text(
            text = stringResource(id = page.title),
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        )
        Text(
            text =stringResource(id = page.description),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xFFB0B0B0),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
     val pages = listOf(
        OnBoardModel(
            title = R.string.onboardTitle1,
            description = R.string.onboardDes1,
            imageRes = R.drawable.onboard_image_1
        ),
        OnBoardModel(
            title = R.string.onboardTitle2,
            description = R.string.onboardDes2,
            imageRes = R.drawable.onboard_image_2
        ),
        OnBoardModel(
            title = R.string.onboardTitle3,
            description = R.string.onboardDes3,
            imageRes = R.drawable.onboard_image_3
        )
    )

    val pagerState = rememberPagerState{pages.size}
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        ){
            val curr=(pagerState.currentPage+1).toString()
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("${pagerState.currentPage + 1}")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFAAAAAA))) { // You can change this color if needed
                        append("/${pages.size}")
                    }
                },
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.clickable {

                }
            )
            Text(
                "Skip", style = TextStyle(
                    color =Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.clickable {

                }
            )
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            OnBoardItem(pages[page])
        }
        val nextString= remember { mutableStateOf("Next") }
        val prevString= remember { mutableStateOf("") }
        LaunchedEffect(pagerState.currentPage) {
            prevString.value = if (pagerState.currentPage > 0) "Prev" else ""
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        ) {

            Text(
                prevString.value, style = TextStyle(
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.clickable {
                    if (pagerState.currentPage > 0) {
                    val prevPage = pagerState.currentPage-1
                    coroutineScope.launch { pagerState.animateScrollToPage(prevPage) }
                    }
                }
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                repeat(pages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(if (isSelected) 20.dp else 8.dp)
                            .height(if (isSelected) 8.dp else 8.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF707784),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(
                                color = if (isSelected) Color(0xFF3B6C64) else Color(0xFFFFFFFF),
                                shape = CircleShape
                            )
                    )
                }
            }
            Text(
                text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF83758),
                modifier = Modifier.clickable {
                    if (pagerState.currentPage < pages.lastIndex) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        // Last page, call onFinish
                        onFinish()
                    }
                }
            )
        }
    }
}








