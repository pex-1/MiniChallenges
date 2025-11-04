package com.example.minichallenges.challenges.september

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.data.ticketOptions
import com.example.minichallenges.challenges.september.theme.SeptemberTheme
import com.example.minichallenges.challenges.september.theme.lime
import com.example.minichallenges.challenges.september.theme.surfaceHigher
import com.example.minichallenges.challenges.september.theme.textDisabled
import com.example.minichallenges.challenges.september.theme.textTertiary

data class TicketOption(val name: String, val price: Int)

@Composable
fun TicketBuilder() {
    SeptemberTheme {
        var selectedOption by remember { mutableStateOf<TicketOption?>(null) }
        var quantity by remember { mutableIntStateOf(1) }

        val total = (selectedOption?.price ?: 0) * quantity

        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(vertical = 16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(IntrinsicSize.Min),
                    text = stringResource(R.string.ticket_builder),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = stringResource(R.string.select_ticket_type_quantity),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
                )


                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(surfaceHigher)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.ticket_type),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = textTertiary
                        )
                    )

                    ticketOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { selectedOption = option },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option },
                                    colors = RadioButtonDefaults.colors(
                                        unselectedColor = MaterialTheme.colorScheme.primary,
                                        selectedColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = option.name,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            Text(
                                text = "$${option.price}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }

                    // Quantity selector
                    Text(
                        text = stringResource(R.string.quantity),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = textTertiary
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        QuantitySelector(quantity) { newQuantity ->
                            quantity = newQuantity.coerceAtLeast(1)
                        }
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 2.dp
                    )

                    // Total section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.total),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "$$total",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }


                // Purchase button
                val isEnabled = selectedOption != null && quantity > 0
                Button(
                    onClick = { /* handle purchase */ },
                    enabled = isEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lime,
                        disabledContainerColor = surfaceHigher,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = null
                ) {
                    Text(
                        text = "Purchase",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isEnabled) MaterialTheme.colorScheme.primary else textDisabled
                    )
                }
            }
        }
    }
}

@Composable
fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            tint = if (quantity > 1) MaterialTheme.colorScheme.primary else textDisabled,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    enabled = quantity > 1,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onQuantityChange(quantity - 1)
                }
                .clip(RoundedCornerShape(8.dp))
                .background(if (quantity > 1) MaterialTheme.colorScheme.background else Color.Transparent)
                .padding(20.dp)
        )

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.labelLarge
        )

        Icon(
            imageVector = Icons.Default.Add,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onQuantityChange(quantity + 1)
                }
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TicketBuilderPreview() {
    SeptemberTheme {
        TicketBuilder()
    }
}