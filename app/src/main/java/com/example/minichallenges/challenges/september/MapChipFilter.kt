package com.example.minichallenges.challenges.september

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.data.filters
import com.example.minichallenges.challenges.september.theme.SeptemberTheme
import com.example.minichallenges.challenges.september.theme.textDisabled
import com.example.minichallenges.challenges.september.theme.textPrimary

data class MapChip(
    val label: String,
    val color: Color,
    val type: MarkerType,
    val mapImage: Int,
    val icon: ImageVector
)

enum class MarkerType { STAGE, FOOD, WC }

@Composable
fun MapChipFilter() {
    var activeFilters by remember { mutableStateOf(setOf<MarkerType>()) }

    fun toggleFilter(type: MarkerType) {
        activeFilters = if (type in activeFilters)
            activeFilters.minus(type)
        else
            activeFilters.plus(type)
    }

    SeptemberTheme {

        Scaffold { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
            {
                Text(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 20.dp),
                    text = stringResource(R.string.festival_map),
                    style = MaterialTheme.typography.labelLarge
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { filter ->
                        val isSelected = activeFilters.contains(filter.type)
                        MapFilter(isSelected, filter) {
                            toggleFilter(filter.type)
                        }
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = stringResource(R.string.festival_map),
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    filters.forEach { filter ->
                        if (filter.type in activeFilters) {
                            Image(
                                painter = painterResource(id = filter.mapImage),
                                contentDescription = filter.label,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun MapFilter(
    isSelected: Boolean,
    filter: MapChip,
    onClick: () -> Unit = {}
) {
    val textColor = if (isSelected) textPrimary else textDisabled

    FilterChip(
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        selected = isSelected,
        onClick = {
            onClick()
        },
        label = {
            Text(
                filter.label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor
                )
            )
        },
        leadingIcon = {
            Icon(
                filter.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = textColor
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = if (isSelected) filter.color else Color.Transparent,
            selectedContainerColor = filter.color,
            disabledContainerColor = Color.Transparent
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = textColor
        )
    )
}


@Preview
@Composable
private fun MapChipFilterPreview() {
    SeptemberTheme {
        MapChipFilter()
    }
}