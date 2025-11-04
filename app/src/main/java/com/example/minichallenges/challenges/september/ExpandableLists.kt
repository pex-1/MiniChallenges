package com.example.minichallenges.challenges.september

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.data.stages
import com.example.minichallenges.challenges.september.theme.SeptemberTheme
import com.example.minichallenges.challenges.september.theme.textPrimary

data class Performer(val name: String, val time: String)
data class Stage(val name: String, val color: Color, val performers: List<Performer>)

@Composable
fun ExpandableLists() {
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(R.string.festival_lineup),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = stringResource(R.string.tap_a_stage_to_view_performers),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp, start = 10.dp, end = 10.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(stages) { index, stage ->
                    ExpandableStageCard(
                        title = stage.name,
                        backgroundColor = stage.color,
                        isExpanded = expandedIndex == index,
                        performers = stage.performers,
                        onClick = {
                            expandedIndex = if (expandedIndex == index) null else index
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableStageCard(
    title: String,
    backgroundColor: Color,
    isExpanded: Boolean,
    performers: List<Performer>,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                indication = null,      //disable ripple effect
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .animateContentSize()
            .graphicsLayer {
                clip = true     //ensures no sharp edge flashing during animation
                shape = RoundedCornerShape(16.dp)
            },
        color = backgroundColor
    ) {
        Column(modifier = Modifier.padding(vertical = 40.dp, horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.Remove else Icons.Default.Add,
                    contentDescription = null,
                    tint = textPrimary
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    performers.forEachIndexed { i, performer ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = performer.name,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                text = performer.time
                            )
                        }

                        if (i != performers.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 20.dp),
                                color = textPrimary,
                                thickness = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ExpandableListsPreview() {
    SeptemberTheme {
        ExpandableLists()
    }
}