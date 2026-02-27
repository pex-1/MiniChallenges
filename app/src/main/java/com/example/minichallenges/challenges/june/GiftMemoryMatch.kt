package com.example.minichallenges.challenges.june

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import kotlinx.coroutines.delay

// data

val LeafGreen = Color(0xFFBDE372)
val CaramelBrown = Color(0xFFAA5A46)
val RosePink = Color(0xFFF6C1C7)
val ElectricBlue = Color(0xFF00B2FF)
val CherryRed = Color(0xFFD72D3B)
val SteelGray = Color(0xFFB7BCBF)
val Tangerine = Color(0xFFFFA228)
val Lavender = Color(0xFFD0BAF9)
val CanvasBeige = Color(0xFFFBE7CD)
val HoneyGold = Color(0xFFFFD700)
val IcyBlue = Color(0xFFA5DAFF)
val LemonYellow = Color(0xFFFDF267)
val ForestGreen = Color(0xFF58A05D)
val Cinnamon = Color(0xFFCD804B)
val GraniteGray = Color(0xFFE6E6E6)
val CocoaBrown = Color(0xFF6D3E1B)
val SkyBlue = Color(0xFF69DEF5)
val PartyPink = Color(0xFFFF92D3)
val CharcoalGray = Color(0xFF3E3F41)
val BoulderGray = Color(0xFF7D7B75)

data class GiftItem(
    val recipient: String,
    val gift: String,
    val colorName: String,
    val color: Color
)

val gifts = listOf(
    GiftItem("Lily", "Watering can", "Leaf Green", LeafGreen),
    GiftItem("Chip", "Cookie tin", "Caramel Brown", CaramelBrown),
    GiftItem("Rose", "Scented candle", "Rose Pink", RosePink),
    GiftItem("Buzz", "Toy drone", "Electric Blue", ElectricBlue),
    GiftItem("Dot", "Polka mug", "Cherry Red", CherryRed),
    GiftItem("Max", "Dumbbell keychain", "Steel Gray", SteelGray),
    GiftItem("Finn", "Goldfish plush", "Tangerine", Tangerine),
    GiftItem("Belle", "Hand mirror", "Lavender", Lavender),
    GiftItem("Art", "Paint palette", "Canvas Beige", CanvasBeige),
    GiftItem("Mel", "Honey jar", "Honey Gold", HoneyGold),
    GiftItem("Noel", "Snow globe", "Icy Blue", IcyBlue),
    GiftItem("Sunny", "Sun hat", "Lemon Yellow", LemonYellow),
    GiftItem("Ivy", "Hanging plant", "Forest Green", ForestGreen),
    GiftItem("Ginger", "Spice rack", "Cinnamon", Cinnamon),
    GiftItem("Rocky", "Pet rock", "Granite Gray", GraniteGray),
    GiftItem("Coco", "Hot cocoa mix", "Cocoa Brown", CocoaBrown),
    GiftItem("Bree", "Wind chime", "Sky Blue", SkyBlue),
    GiftItem("Joy", "Confetti popper", "Party Pink", PartyPink),
    GiftItem("Ash", "Campfire candle", "Charcoal Gray", CharcoalGray),
    GiftItem("Brock", "Onix figure", "Boulder Gray", BoulderGray)
)

val recipientNames = gifts.map { it.recipient }.toSet()

data class GiftCards(
    val id: Int,
    val text: String,
    val color: Color,
    val pairId: Int
)

fun shuffleCards(): List<GiftCards> {
    var idCounter = 0
    val giftsOnlySixPairs = gifts.shuffled().take(6)
    return giftsOnlySixPairs.flatMapIndexed { index, item ->
        val recipientCard = GiftCards(
            id = idCounter++,
            text = item.recipient,
            color = item.color,
            pairId = index
        )
        val giftCard = GiftCards(
            id = idCounter++,
            text = item.gift,
            color = item.color,
            pairId = index
        )
        listOf(recipientCard, giftCard)
    }.shuffled()
}



// screen

@Composable
fun GiftMemoryMatchScreen(modifier: Modifier = Modifier) {

    val allCards = remember { shuffleCards() }
    var cards by remember { mutableStateOf(allCards.shuffled()) }

    var flippedCards by remember { mutableStateOf(listOf<Int>()) }
    var matchedCards by remember { mutableStateOf(setOf<Int>()) }
    var matchesFound by remember { mutableStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }

    val totalPairs = 6
    val progress = matchesFound.toFloat() / totalPairs

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A597B))
            .paint(
                painter = painterResource(R.drawable.decor),
                contentScale = ContentScale.Fit
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // progress
            Text(
                text = "$matchesFound of $totalPairs matches found",
                color = Color(0xFFFFF5EB),
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = maliFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LinearProgressIndicator(
                progress = progress,
                color = Color(0xFF96F7C2),
                trackColor = Color.White.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // gift card grid
            val rows = 4
            val columns = 3
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(rows) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(columns) { col ->
                            val index = row * columns + col
                            if (index < cards.size) {
                                val card = cards[index]
                                val flipped = index in flippedCards || index in matchedCards

                                FlipCard(
                                    flipped = flipped,
                                    frontSide = card.text,
                                    borderColor = card.color,
                                    onClick = {
                                        if (flippedCards.size < 2 && index !in flippedCards && index !in matchedCards) {
                                            flippedCards = flippedCards + index
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // matching
            LaunchedEffect(flippedCards) {
                if (flippedCards.size == 2) {
                    delay(1000)
                    val first = cards[flippedCards[0]]
                    val second = cards[flippedCards[1]]

                    if (first.color == second.color) {
                        matchedCards = matchedCards + flippedCards
                        matchesFound++
                        if (matchesFound == totalPairs) {
                            delay(500)
                            showDialog = true
                        }
                    }
                    flippedCards = listOf()
                }
            }
        }

        // success Dialog
        if (showDialog) {
            AlertDialogBox(
                onClick = {
                    cards = shuffleCards().shuffled()
                    flippedCards = listOf()
                    matchedCards = setOf()
                    matchesFound = 0
                    showDialog = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun GiftMemoryMatchScreenPreview() {
    GiftMemoryMatchScreen()
}


// flip card

@Composable
fun FlipCard(
    flipped: Boolean,
    onClick: () -> Unit,
    frontSide: String,
    borderColor: Color
) {
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = !flipped) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            // backside
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF5EB)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.stripes),
                    contentDescription = null
                )
            }
        } else {
            // front side
            Box(
                modifier = Modifier
                    .graphicsLayer { rotationY = 180f }
                    .fillMaxSize()
                    .background(Color.White)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 5.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                val painter = if (frontSide in recipientNames) {
                    R.drawable.guest
                } else {
                    R.drawable.gift
                }
                Image(
                    painter = painterResource(painter),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                if (frontSide in recipientNames) {
                    Text(
                        text = frontSide,
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = maliFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = frontSide,
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = maliFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun FlipCardPreview() {
    FlipCard(
        flipped = true,
        onClick = {},
        frontSide = "front side",
        borderColor = Color.Red
    )
}


// alert dialog

@Composable
fun AlertDialogBox(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Transparent.copy(alpha = 0.4f)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(500.dp, 500.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = Color(0xFFFFF5EB)
                )
                .paint(
                    painter = painterResource(R.drawable.birthday_bg),
                    contentScale = ContentScale.Fit
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Party on!",
                    color = Color(0xFF113345),
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = maliFamily,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "All gifts matched",
                    color = Color(0xFF113345),
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = maliFamily,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF95D3ED)
                    )
                ) {
                    Text(
                        text = "Try Again",
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = maliFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlertDialogBoxPreview() {
    AlertDialogBox({})
}



