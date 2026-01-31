package com.example.minichallenges.challenges.june

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_FOLD
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.minichallenges.R

@Composable
fun BirthdayCardScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF1A597B)
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        BirthdayCard()
    }
}

@Composable
fun BirthdayCard(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .background(
                color = Color(0xFFFFF5EB),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .paint(
                painter = painterResource(R.drawable.birthday_bg),
                contentScale = ContentScale.Fit
            )
    ) {
        val (details, rsvp) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(details) {
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(rsvp.top, margin = 16.dp)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You're Invited!",
                color = Color(0xFF113345),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = maliFamily,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Join us for a birthday bash \uD83C\uDF89",
                color = Color(0xFF113345),
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = maliFamily,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = getAnnotatedString(
                    dtl = "Date",
                    value = "June 14, 2025"
                ),
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF113345),
                fontFamily = nunitoFamily,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = getAnnotatedString(
                    dtl = "Time",
                    value = "3:00 PM"
                ),
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF113345),
                fontFamily = nunitoFamily,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = getAnnotatedString(
                    dtl = "Location",
                    value = "Party Central, 123 Celebration Lane"
                ),
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF113345),
                fontFamily = nunitoFamily,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

        Text(
            modifier = Modifier.constrainAs(rsvp) {
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            text = "RSVP by June 9",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = nunitoFamily,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF113345).copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun getAnnotatedString(dtl: String, value: String): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(
            SpanStyle(
                fontFamily = nunitoFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF113345)
            )
        )
        append(dtl)
        append(": ")
        pushStyle(
            SpanStyle(
                fontFamily = nunitoFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF113345).copy(alpha = 0.8f)
            )
        )
        append(value)
        pop()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BirthdayCardInviteScreenPreview() {
    BirthdayCardScreen()
}

@Preview(showBackground = true, showSystemUi = true, device = PIXEL_FOLD)
@Composable
private fun BirthdayCardInviteScreenTabletPreview() {
    BirthdayCardScreen()
}