package com.example.minichallenges.challenges.january.profileavatareditor

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.ProfileAvatarEditorTheme
import java.io.File

@Preview
@Composable
private fun ProfileAvatarEditorPreview() {
    ProfileAvatarEditorTheme {
        ProfileAvatarEditor(Any())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAvatarEditor(flag: Any?, openEditorScreen: (Uri) -> Unit = {}) {

    val photoPickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                openEditorScreen(uri)
            }
        }

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    var userImage: File? by remember { mutableStateOf(null) }

    LaunchedEffect(flag) {
        val file = File(context.cacheDir, "user_avatar.png")
        if (file.exists()) {
            userImage = file
        }

        if (flag != null) {
            snackBarHostState.showSnackbar("Avatar updated successfully.")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontFamily = JakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.small
                            )
                            .clip(MaterialTheme.shapes.small)
                            .clickable(onClick = {})
                            .padding(6.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserImage(image = userImage, onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            })
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }, colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.outline
                ),
                contentPadding = PaddingValues(
                    vertical = 10.dp,
                    horizontal = 16.dp
                )
            ) {
                Text(
                    text = "Change Avatar",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontFamily = JakartaSans
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "John Doe",
                fontWeight = FontWeight.SemiBold,
                fontFamily = JakartaSans,
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "john.doe@example.com",
                fontWeight = FontWeight.Normal,
                fontFamily = JakartaSans,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dropShadow(
                        shape = MaterialTheme.shapes.medium,
                        shadow = Shadow(
                            radius = 8.dp,
                            offset = DpOffset(0.dp, 2.dp),
                            color = Color(0x0F071625)
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                SettingsRow(
                    icon = R.drawable.ic_user,
                    title = "Profile details",
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                SettingsRow(
                    icon = R.drawable.ic_lock,
                    title = "Login & Security",
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                SettingsRow(
                    icon = R.drawable.ic_bell,
                    title = "Notifications",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .dropShadow(
                        shape = MaterialTheme.shapes.medium,
                        shadow = Shadow(
                            radius = 8.dp,
                            offset = DpOffset(0.dp, 2.dp),
                            color = Color(0x0F071625)
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                SettingsRow(
                    icon = R.drawable.ic_log_out,
                    iconColor = MaterialTheme.colorScheme.error,
                    iconContainerColor = MaterialTheme.colorScheme.errorContainer,
                    textColor = MaterialTheme.colorScheme.error,
                    title = "Log out",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsRowPreview() {
    ProfileAvatarEditorTheme {
        SettingsRow(
            icon = R.drawable.ic_arrow_left,
            title = "Change Password"
        )
    }
}

@Composable
fun SettingsRow(
    icon: Int,
    title: String,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
    iconContainerColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    showRightIcon: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = iconContainerColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = iconColor
            )
        }

        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontFamily = JakartaSans,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.weight(1F)
        )

        if (showRightIcon) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = "Arrow Right",
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Preview
@Composable
private fun UserImagePreview() {
    ProfileAvatarEditorTheme {
        UserImage()
    }
}

@Composable
fun UserImage(image: File? = null, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(136.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .border(shape = CircleShape, color = MaterialTheme.colorScheme.outline, width = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        if (image == null) {
            Image(
                painter = painterResource(R.drawable.ic_def_img),
                contentDescription = "default User Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(CircleShape)
            )

            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "Add Avatar icon",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(CircleShape)
            )
        }
    }
}