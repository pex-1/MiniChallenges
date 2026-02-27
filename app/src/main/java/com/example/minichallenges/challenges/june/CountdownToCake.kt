@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.minichallenges.challenges.june

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.example.minichallenges.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CountdownCakeScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var showCountdown by remember { mutableStateOf(false) }
    var countdownValue by remember { mutableStateOf(5) }
    var isRunning by remember { mutableStateOf(false) }

    val notificationPermission = rememberPermissionState(
        POST_NOTIFICATIONS
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(showCountdown, isRunning) {
        if (showCountdown && isRunning) {
            for (i in countdownValue downTo 1) {
                countdownValue = i
                delay(1000)
            }
            showCountdown = false
            isRunning = false
            if (notificationPermission.status.isGranted) {
                cakeNotification(
                    context,
                    "\uD83C\uDF89 Itʼs cake time!",
                    "Countdown timer ended"
                )
            }
        }
    }

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
        // cake
        Image(
            painter = painterResource(R.drawable.cake),
            contentDescription = null,
            modifier = Modifier
                .offset(y = 280.dp)
        )
        if (!showCountdown) {
            // count to cake button
            Button(
                onClick = {
                    scope.launch {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (!notificationPermission.status.isGranted) {
                                notificationPermission.launchPermissionRequest()
                            }
                        }
                        showCountdown = true
                        isRunning = true
                        countdownValue = 5
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFF5EB)
                ),
                modifier = Modifier
                    .offset(
                        y = (-200).dp
                    )
            ) {
                Text(
                    text = "Count to Cake!",
                    color = Color(0xFF113345),
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = maliFamily,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .offset(y = 70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // lighted candle
                Image(
                    painter = painterResource(R.drawable.candle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    // timer from 5 to 1
                    Text(
                        text = countdownValue.toString(),
                        fontSize = 290.sp,
                        color = Color(0xFF95D3ED),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = maliFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .offset(
                                x = (-10).dp,
                                y = (-160).dp
                            )
                    )
                }
                // cancel timer button
                Button(
                    onClick = {
                        showCountdown = false
                        isRunning = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFF5EB)
                    )
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFFD02A27),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = maliFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun CountdownCakeScreenPreview() {
    CountdownCakeScreen()
}


// cake notification

fun cakeNotification(
    context: Context,
    title: String,
    message: String
) {
    val cakeChannelId = "cake_notification"
    val cakeNotificationId = 2010

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            cakeChannelId,
            "Cake Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notification that timer ends"
        }

        val manager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, cakeChannelId)
        .setSmallIcon(R.drawable.gifts)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
//        notify(cakeNotificationId, builder.build())
    }
}



