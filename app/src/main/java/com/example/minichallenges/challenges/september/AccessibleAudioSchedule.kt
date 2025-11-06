package com.example.minichallenges.challenges.september

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.data.scheduleList
import com.example.minichallenges.challenges.september.theme.SeptemberTheme
import com.example.minichallenges.challenges.september.theme.blue
import com.example.minichallenges.challenges.september.theme.lime
import com.example.minichallenges.challenges.september.theme.orange
import com.example.minichallenges.challenges.september.theme.pink
import com.example.minichallenges.challenges.september.theme.purple
import com.example.minichallenges.challenges.september.theme.surfaceHigher

data class Performance(
    val name: String,
    val time: String,
    val stage: String,
    val genre: Genre,
    val description: String
)

enum class Genre(val label: String, val color: Color) {
    INDIE("Indie", orange),
    ELECTRONIC("Electronic", lime),
    HEADLINER("Headliner", purple),
    EXPERIMENTAL("Experimental", pink),
    ALT_ROCK("Alt Rock", blue)
}

@Composable
fun AccessibleAudioSchedule() {
    SeptemberTheme {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 12.dp)
                        .padding(top = 28.dp, bottom = 20.dp),
                    text = stringResource(R.string.schedule),
                    style = MaterialTheme.typography.bodyLarge
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(scheduleList, key = { it.name }) { performance ->
                        PerformanceItem(performance)
                    }
                }
            }
        }
    }
}

@Composable
fun PerformanceItem(performance: Performance) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(surfaceHigher)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(performance.genre.color)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            text = performance.genre.label,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary),
        )
        Row(
            modifier = Modifier
                .clearAndSetSemantics {
                    contentDescription =
                        "${performance.name}, ${performance.time}, ${performance.stage}"
                }
                .fillMaxWidth()
                .basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    iterations = 2,
                    repeatDelayMillis = 40,
                    velocity = 40.dp,
                    initialDelayMillis = 1000,
                    spacing = MarqueeSpacing(40.dp)
                )
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(end = 20.dp),
                text = performance.name,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
            )
            Text(
                text = performance.time + " • " + performance.stage,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Text(
            text = performance.description,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
        )

    }
}

@Preview
@Composable
private fun AccessibleAudioSchedulePreview() {
    SeptemberTheme {
        AccessibleAudioSchedule()
    }
}