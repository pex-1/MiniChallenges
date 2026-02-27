package com.example.minichallenges.challenges.january.januaryrecipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.minichallenges.challenges.january.theme.InstrumentSans
import com.example.minichallenges.challenges.january.theme.InstrumentSerif
import com.example.minichallenges.challenges.january.theme.JanuaryRecipeRefreshTheme

@Composable
fun RecipeDetailDialog(
    onDismissRequest: () -> Unit,
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        RecipeDetailDialogContent(
            recipe = recipe,
            onDone = onDismissRequest,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun RecipeDetailDialogContentPreview() {
    JanuaryRecipeRefreshTheme {
        RecipeDetailDialogContent(
            recipe = Recipe.HOT_SPICED_COCOA,
            onDone = {}
        )
    }
}

@Composable
fun RecipeDetailDialogContent(recipe: Recipe, onDone: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 520.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(4.dp)

    ) {
        Image(
            painter = painterResource(recipe.imageRes),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.title,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = InstrumentSerif,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = recipe.ingredients,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontStyle = FontStyle.Italic,
            fontFamily = InstrumentSans,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = recipe.description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = InstrumentSans,
            lineHeight = 24.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .clickable(onClick = onDone)
                .padding(
                    vertical = 10.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Done",
                fontFamily = InstrumentSans,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}