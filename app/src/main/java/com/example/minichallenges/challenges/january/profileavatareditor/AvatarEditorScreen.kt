package com.example.minichallenges.challenges.january.profileavatareditor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.primaryGradientEnd
import com.example.minichallenges.challenges.january.theme.primaryGradientStart
import com.example.minichallenges.challenges.january.theme.surfaceAlt
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarEditorScreen(uri: String, onBack: () -> Unit, onImageSaved: () -> Unit) {
    val context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Edit Avatar",
                    fontFamily = JakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceAlt,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(6.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable(onClick = onBack)
                ) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }, containerColor = MaterialTheme.colorScheme.onBackground) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))


            var offset by remember {
                mutableStateOf(Offset(200F, 200F))
            }

            var size by remember {
                mutableStateOf(Size(400F, 400F))
            }
            val graphicsLayer = rememberGraphicsLayer()
            val coroutineScope = rememberCoroutineScope()

            AvatarEditorComponent(
                uri = Uri.parse(Uri.decode(uri)),
                size = size,
                offset = offset,
                onOffsetChange = { newOffset ->
                    offset = newOffset
                },
                graphicsLayer = graphicsLayer,
                onSizeChange = { newSize ->
                    size = newSize
                },
            )

            Spacer(modifier = Modifier.height(64.dp))

            Box(
                modifier = Modifier
                    .width(280.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryGradientStart,
                                MaterialTheme.colorScheme.primaryGradientEnd,
                            )
                        ),
                        shape = CircleShape
                    )
                    .clickable(onClick = {
                        coroutineScope.launch {
                            val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                            context.saveImageAsAvatar(
                                bitmap = bitmap,
                                offset = offset,
                                size = size,
                            )
                            onImageSaved()
                        }
                    })
                    .clip(CircleShape)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save", fontFamily = JakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                )
            }
        }
    }
}

private fun Context.saveImageAsAvatar(bitmap: Bitmap, offset: Offset, size: Size) {
    val avatar = Bitmap.createBitmap(
        bitmap,
        offset.x.toInt(),
        offset.y.toInt(),
        size.width.toInt(),
        size.height.toInt()
    )

    val file = File(cacheDir, "user_avatar.png")
    if (file.exists()) {
        file.delete()
        file.createNewFile()
    }
    avatar.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(file))
}