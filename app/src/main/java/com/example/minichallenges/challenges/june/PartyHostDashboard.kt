package com.example.minichallenges.challenges.june

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch

enum class BottomBarScreen(
    val title: String,
    val icon: Int
) {
    GUEST_LIST("Guest List", R.drawable.guest_list),
    PARTY_TIMELINE("Party Timeline", R.drawable.party_timeline),
    GIFTS("Gifts", R.drawable.gifts)
}

@Composable
fun PartyHostDashBoard(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5EB))
            .paint(
                painter = painterResource(R.drawable.decor),
                contentScale = ContentScale.Fit,
                alpha = 0.2f,
            )
    ) {
        var selectedItemIndex by remember { mutableIntStateOf(0) }
        val windowWidthClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                BottomBarScreen.entries.forEachIndexed { index, screen ->
                    item(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(text = screen.title)
                        }
                    )
                }
            },
            layoutType = if (windowWidthClass == WindowWidthSizeClass.EXPANDED) {
                NavigationSuiteType.WideNavigationRailCollapsed
            } else {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                    currentWindowAdaptiveInfo()
                )
            },
            containerColor = Color(0xFFFFFFFF),
        ) {
            when (selectedItemIndex) {
                0 -> GuestListScreen()
                1 -> PartyTimelineScreen()
                2 -> GiftsScreen()
            }
        }
    }
}

@Preview
@Composable
private fun PartyHostDashBoardPreview() {
    PartyHostDashBoard()
}


data class Guest(
    val name: String,
    @StringRes val status: Int,
    @StringRes val gift: Int
)

private val guestsList = listOf(
    Guest("Alice Bennett", R.string.status_attending, R.string.gift_journal),
    Guest("Daniel Cho", R.string.status_not_coming, R.string.gift_mug),
    Guest("Zoë Linde", R.string.status_attending, R.string.gift_chocolate),
    Guest("Mr. Whiskers", R.string.status_maybe, R.string.gift_catnip),
    Guest("Ava Rodriguez", R.string.status_attending, R.string.gift_photo_frame),
    Guest("Chinedu Okwudili", R.string.status_not_coming, R.string.gift_book),
    Guest("Lily Thompson", R.string.status_attending, R.string.gift_painting),
    Guest("James “Jimmy” Kwon", R.string.status_attending, R.string.gift_speaker),
    Guest("Priya Mehra", R.string.status_maybe, R.string.gift_bakery_card),
    Guest("Gabriella De La Fuente", R.string.status_attending, R.string.gift_bracelet)
)


// Guest list screen

@Composable
fun GuestListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) {
        val navigator = rememberListDetailPaneScaffoldNavigator<Guest>()
        val scope = rememberCoroutineScope()
        NavigableListDetailPaneScaffold(
            modifier = Modifier,
            navigator = navigator,
            listPane = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF5EB))
                        .paint(
                            painter = painterResource(R.drawable.decor),
                            contentScale = ContentScale.Fit,
                            alpha = 0.2f,
                        )
                        .padding(24.dp)
                        .padding(vertical = 48.dp)
                ) {
                    Text(
                        text = "Party Host Dashboard",
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = nunitoFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        guestsList.forEach { guest ->
                            Row(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    )
                                    .clickable {
                                        scope.launch {
                                            navigator.navigateTo(
                                                pane = ListDetailPaneScaffoldRole.Detail,
                                                contentKey = guest
                                            )
                                        }
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = guest.name,
                                    color = Color(0xFF113345),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontFamily = nunitoFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                val color = when (guest.status) {
                                    R.string.status_attending -> Color(0xFF58A05D)
                                    R.string.status_not_coming -> Color(0xFF7D7B75)
                                    else -> Color(0xFF00B2FF)
                                }
                                Text(
                                    text = stringResource(guest.status),
                                    color = color,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontFamily = maliFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                    }
                }
            },
            detailPane = {
                val currentGuest = navigator.currentDestination?.contentKey
                AnimatedPane {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFFF5EB))
                            .paint(
                                painter = painterResource(R.drawable.decor),
                                contentScale = ContentScale.Fit,
                                alpha = 0.2f,
                            )
                            .padding(24.dp)
                            .padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        navigator.navigateBack()
                                    }
                                },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color(0xFFFFF5EB),
                                    contentColor = Color(0xFF113345)
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_button),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "Guest",
                                color = Color(0xFF113345),
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = nunitoFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFFFFF)
                            )
                        ) {
                            if (currentGuest != null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                horizontal = 16.dp,
                                                vertical = 12.dp
                                            ),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = currentGuest.name,
                                            color = Color(0xFF113345),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontFamily = nunitoFamily,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        val color = when (currentGuest.status) {
                                            R.string.status_attending -> Color(0xFF58A05D)
                                            R.string.status_not_coming -> Color(0xFF7D7B75)
                                            else -> Color(0xFF00B2FF)
                                        }
                                        Text(
                                            text = stringResource(currentGuest.status),
                                            color = color,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontFamily = maliFamily,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .padding(bottom = 16.dp)
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "GIFT",
                                            color = Color(0xcc113345),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontFamily = maliFamily,
                                            fontWeight = FontWeight.Normal
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = stringResource(currentGuest.gift),
                                            color = Color(0xFF113345),
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontFamily = nunitoFamily,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Select a guest from the list to see their details.",
                                        color = Color(0xFF113345),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontFamily = nunitoFamily,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun GuestListScreenPreview() {
    GuestListScreen()
}


data class PartyTimeline(
    val time: String,
    val event: Int,
    val eventDetails: Int
)

private val partyTimelineList = listOf(
    PartyTimeline("14:00", R.string.event_venue_setup, R.string.venue_setup_details),
    PartyTimeline("15:00", R.string.event_welcome_arrival, R.string.welcome_arrival_details),
    PartyTimeline("15:30", R.string.event_cake_cutting, R.string.cake_cutting_details),
    PartyTimeline("16:00", R.string.event_party_games, R.string.party_games_details),
    PartyTimeline("16:45", R.string.event_face_painting, R.string.face_painting_details),
    PartyTimeline("17:15", R.string.event_dinner, R.string.dinner_details),
    PartyTimeline("18:00", R.string.event_speeches, R.string.speeches_details),
    PartyTimeline("18:30", R.string.event_dance_floor, R.string.dance_floor_details),
    PartyTimeline("19:30", R.string.event_goodbyes, R.string.goodbyes_details),
)


// Party timeline screen

@Composable
fun PartyTimelineScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) {
        val navigator = rememberListDetailPaneScaffoldNavigator<PartyTimeline>()
        val scope = rememberCoroutineScope()
        NavigableListDetailPaneScaffold(
            modifier = modifier,
            navigator = navigator,
            listPane = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF5EB))
                        .paint(
                            painter = painterResource(R.drawable.decor),
                            contentScale = ContentScale.Fit,
                            alpha = 0.2f,
                        )
                        .padding(24.dp)
                        .padding(vertical = 48.dp)
                ) {
                    Text(
                        text = "Party Host Dashboard",
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = nunitoFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        partyTimelineList.forEach { partyTimeline ->
                            Row(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                    )
                                    .clickable {
                                        scope.launch {
                                            navigator.navigateTo(
                                                pane = ListDetailPaneScaffoldRole.Detail,
                                                contentKey = partyTimeline
                                            )
                                        }
                                    },
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = partyTimeline.time,
                                    color = Color(0xFF1A597B),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontFamily = maliFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = stringResource(partyTimeline.event),
                                    color = Color(0xFF113345),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontFamily = nunitoFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                }
            },
            detailPane = {
                val currentEvent = navigator.currentDestination?.contentKey
                AnimatedPane {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFFF5EB))
                            .paint(
                                painter = painterResource(R.drawable.decor),
                                contentScale = ContentScale.Fit,
                                alpha = 0.2f,
                            )
                            .padding(24.dp)
                            .padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        navigator.navigateBack()
                                    }
                                },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = Color(0xFFFFF5EB),
                                    contentColor = Color(0xFF113345)
                                )
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_button),
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "Guest",
                                color = Color(0xFF113345),
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = nunitoFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFFFFF)
                            )
                        ) {
                            if (currentEvent != null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = currentEvent.time,
                                            color = Color(0xFF1A597B),
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontFamily = maliFamily,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = stringResource(currentEvent.event),
                                            color = Color(0xFF113345),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontFamily = nunitoFamily,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = stringResource(currentEvent.eventDetails),
                                            color = Color(0xCC113345),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontFamily = nunitoFamily,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Select a party event from the list to see their details.",
                                        color = Color(0xFF113345),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontFamily = nunitoFamily,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun PartyTimelineScreenPreview() {
    PartyTimelineScreen()
}


// Party timeline screen

@Composable
fun GiftsScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) {
        val navigator = rememberListDetailPaneScaffoldNavigator<Guest>()
        NavigableListDetailPaneScaffold(
            modifier = Modifier,
            navigator = navigator,
            listPane = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF5EB))
                        .paint(
                            painter = painterResource(R.drawable.decor),
                            contentScale = ContentScale.Fit,
                            alpha = 0.2f,
                        )
                        .padding(24.dp)
                        .padding(vertical = 48.dp)
                ) {
                    Text(
                        text = "Party Host Dashboard",
                        color = Color(0xFF113345),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = nunitoFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(guestsList) { guest ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFFFFF)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 12.dp
                                        ),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(guest.gift),
                                        color = Color(0xFF113345),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontFamily = nunitoFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = guest.name,
                                        color = Color(0xCC113345),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = nunitoFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            },
            detailPane = {

            }
        )
    }
}

@Preview
@Composable
private fun GiftsScreenPreview() {
    GiftsScreen()
}
