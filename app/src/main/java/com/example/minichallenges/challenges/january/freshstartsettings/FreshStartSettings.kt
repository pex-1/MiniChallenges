package com.example.minichallenges.challenges.january.freshstartsettings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.theme.FreshStartSettingsTheme
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.outlineInput
import com.example.minichallenges.challenges.january.theme.surfaceInput
import java.text.SimpleDateFormat
import java.util.Locale


@Preview
@Composable
private fun FreshStartSettingsPreview() {
    FreshStartSettingsTheme {
        FreshStartSettings()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreshStartSettings(onBack: () -> Unit = {}) {
    val viewModel: FreshStartSettingsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(onClick = onBack)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_left),
                            contentDescription = "Back to list screen",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = JakartaSans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsRowContainer {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Winter Notification",
                        fontFamily = JakartaSans,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Switch(
                        checked = uiState.isNotificationsEnabled,
                        onCheckedChange = {
                            viewModel.onIntent(
                                FreshStartSettingsIntent.UpdateNotificationsEnabled(isEnabled = it)
                            )
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.surface,
                            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            checkedBorderColor = MaterialTheme.colorScheme.primary,
                            uncheckedTrackColor = MaterialTheme.colorScheme.outlineInput,
                            uncheckedBorderColor = MaterialTheme.colorScheme.outlineInput,
                        )
                    )
                }
            }

            SettingsRowContainer {
                Text(
                    text = "New Year Theme",
                    fontFamily = JakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceInput,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            width = 1.dp,
                            color = if (isDropdownExpanded) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineInput,
                            shape = MaterialTheme.shapes.medium
                        )
                        .clickable { isDropdownExpanded = !isDropdownExpanded }
                        .padding(horizontal = 14.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = uiState.selectedTheme.label,
                        fontFamily = JakartaSans,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                    Icon(
                        painter = painterResource(
                            if (isDropdownExpanded) R.drawable.ic_chevron_up
                            else R.drawable.ic_chevron_down
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    containerColor = MaterialTheme.colorScheme.surfaceInput
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        for (item in NewYearTheme.entries) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        isDropdownExpanded = false
                                        viewModel.onIntent(
                                            FreshStartSettingsIntent.UpdateTheme(theme = item)
                                        )
                                    }
                                    .padding(vertical = 12.dp, horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.label,
                                    fontFamily = JakartaSans,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )

                                if (item == uiState.selectedTheme) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_check),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            SettingsRowContainer {
                Text(
                    text = "Daily Step Goal",
                    fontFamily = JakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                var text by rememberSaveable(uiState.dailyStepGoal) {
                    mutableStateOf(uiState.dailyStepGoal.toString())
                }
                var hasFocus by rememberSaveable { mutableStateOf(false) }

                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                fun commit() {
                    val newValue = text.toIntOrNull() ?: uiState.dailyStepGoal
                    viewModel.onIntent(FreshStartSettingsIntent.UpdateDailyStepGoal(stepGoal = newValue))
                }

                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                        commit()
                    }),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = JakartaSans,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceInput,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            width = 1.dp,
                            color = if (hasFocus) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineInput,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 14.dp, vertical = 16.dp)
                        .onFocusChanged {
                            val nowFocused = it.isFocused
                            val lostFocus = hasFocus && !nowFocused
                            hasFocus = nowFocused
                            if (lostFocus) commit()
                        }
                )
            }

            SettingsRowContainer {
                Text(
                    text = "Motivation Level",
                    fontFamily = JakartaSans,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = uiState.motivationLevel,
                    onValueChange = {
                        viewModel.onIntent(
                            FreshStartSettingsIntent.UpdateMotivationLevel(motivationLevel = it)
                        )
                    },
                    steps = 10,
                    valueRange = 0F..1F,
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.outlineInput,
                    ),
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(width = 12.dp, height = 24.dp)
                                .dropShadow(
                                    shape = MaterialTheme.shapes.small,
                                    shadow = Shadow(
                                        offset = DpOffset(0.dp, 4.dp),
                                        color = Color(0x24071625),
                                        radius = 8.dp,
                                        spread = (-2).dp
                                    )
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    },
                    track = { state ->
                        val activeTrackColor = MaterialTheme.colorScheme.primary
                        val inactiveTrackColor = MaterialTheme.colorScheme.outlineInput
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        ) {
                            drawRoundRect(
                                topLeft = Offset.Zero,
                                size = this.size,
                                color = inactiveTrackColor,
                                cornerRadius = CornerRadius(size.height / 2)
                            )
                            drawRoundRect(
                                topLeft = Offset.Zero,
                                size = this.size.copy(
                                    width = this.size.width * state.value / state.valueRange.endInclusive
                                ),
                                color = activeTrackColor,
                                cornerRadius = CornerRadius(size.height / 2)
                            )
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "0.0",
                        fontFamily = JakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "0.5",
                        fontFamily = JakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape
                            )
                            .padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "1.0",
                        fontFamily = JakartaSans,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            val dateFormatter = remember {
                SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.ENGLISH)
            }
            val formattedDate = remember(uiState.lastUpdatedAt) {
                uiState.lastUpdatedAt?.let(dateFormatter::format) ?: "No changes yet"
            }

            Text(
                text = formattedDate,
                fontFamily = JakartaSans,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun SettingsRowContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = MaterialTheme.shapes.medium,
                shadow = Shadow(
                    offset = DpOffset(0.dp, 2.dp),
                    color = Color(0x0F071625),
                    radius = 8.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(16.dp)
    ) {
        content()
    }
}