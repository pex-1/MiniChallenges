package com.example.minichallenges.challenges.january.januaryrecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class JanuaryRecipeRefreshViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState

    private val _snackBarMessage = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val snackBarMessage: SharedFlow<String> = _snackBarMessage.asSharedFlow()

    init {
        loadInitialData()
    }

    fun onIntent(intent: JanuaryRecipeIntent) {
        viewModelScope.launch {
            when (intent) {
                JanuaryRecipeIntent.RefreshData -> refresh()

                JanuaryRecipeIntent.RemoveRecipe -> {
                    _uiState.value = _uiState.value.copy(activeRecipe = null)
                }

                is JanuaryRecipeIntent.SelectRecipe -> {
                    _uiState.value = _uiState.value.copy(activeRecipe = intent.recipe)
                }

                is JanuaryRecipeIntent.ToggleFavourite -> toggleFavourite(
                    recipe = intent.recipe,
                    isFavorite = intent.isFavorite
                )

                is JanuaryRecipeIntent.UpdateSearchQuery -> updateSearch(intent.query)
            }
        }
    }

    private suspend fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        delay(800)

        val filtered = filter(_uiState.value.searchQuery)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            recipes = orderForRefresh(filtered, _uiState.value.favourites)
        )
    }

    private suspend fun toggleFavourite(recipe: Recipe, isFavorite: Boolean) {
        val old = _uiState.value
        val newFavs = if (isFavorite) {
            _snackBarMessage.tryEmit("Added to Favourites")
            old.favourites + recipe
        } else {
            old.favourites - recipe
        }

        val filtered = filter(old.searchQuery)

        _uiState.value = old.copy(
            favourites = newFavs,
            recipes = orderStable(filtered, newFavs)
        )
    }

    private fun updateSearch(query: String) {
        val filtered = filter(query)
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            recipes = orderStable(filtered, _uiState.value.favourites)
        )
    }

    private fun loadInitialData() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            recipes = orderStable(Recipe.entries, _uiState.value.favourites)
        )
    }

    // ✅ Stable ordering -> “graceful” moves (fav moves to top; rest stays predictable)
    private fun orderStable(recipes: List<Recipe>, favourites: Set<Recipe>): List<Recipe> {
        val (fav, other) = recipes.partition { it in favourites }
        return fav.sortedBy { it.title } + other.sortedBy { it.title }
    }

    // Optional: if you want some variation ONLY on refresh
    private fun orderForRefresh(recipes: List<Recipe>, favourites: Set<Recipe>): List<Recipe> {
        val (fav, other) = recipes.partition { it in favourites }
        return fav.sortedBy { it.title } + other.shuffled()
    }

    private fun filter(searchQuery: String): List<Recipe> {
        if (searchQuery.isBlank()) return Recipe.entries
        val query = searchQuery.trim().lowercase()
        return Recipe.entries.filter { it.title.lowercase().contains(query) }
    }
}

data class RecipeUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = Recipe.entries,
    val favourites: Set<Recipe> = emptySet(),
    val searchQuery: String = "",
    val activeRecipe: Recipe? = null,
)

sealed interface JanuaryRecipeIntent {
    data class ToggleFavourite(val recipe: Recipe, val isFavorite: Boolean) : JanuaryRecipeIntent
    data class UpdateSearchQuery(val query: String) : JanuaryRecipeIntent
    data class SelectRecipe(val recipe: Recipe?) : JanuaryRecipeIntent
    data object RemoveRecipe : JanuaryRecipeIntent
    data object RefreshData : JanuaryRecipeIntent
}

enum class Recipe(
    val imageRes: Int,
    val title: String,
    val ingredients: String,
    val description: String
) {
    CREAMY_MUSHROOM_SOUP(
        R.drawable.creamy_mushroom_soup,
        "Creamy Mushroom Soup",
        "Mushrooms, vegetable broth, onion, garlic, cream, thyme",
        "Sauté onions and garlic until fragrant, then add mushrooms and broth. Simmer gently and finish with cream for a smooth, comforting soup."
    ),
    WINTER_VEGETABLE_STEW(
        R.drawable.winter_vegetable_soup,
        "Winter Vegetable Stew",
        "Carrots, potatoes, parsnips, onion, vegetable broth, rosemary",
        "Cook chopped vegetables with herbs in vegetable broth until tender. Serve hot as a hearty winter stew."
    ),
    SPICED_LENTIL_SOUP(
        R.drawable.spiced_lentil_soup,
        "Spiced Lentil Soup",
        "Red lentils, vegetable broth, carrot, onion, garlic, cumin",
        "Sauté the chopped vegetables with garlic and spices until fragrant, then add lentils and broth. Simmer until the lentils soften and the soup thickens."
    ),
    POTATO_LEEK_COMFORT_SOUP(
        R.drawable.potato_leek_comfort_soup,
        "Potato & Leek Comfort Soup",
        "Potatoes, leeks, butter, vegetable broth, cream",
        "Slowly cook leeks in butter, add potatoes and broth, then simmer until soft. Blend lightly and finish with cream."
    ),
    GINGER_HONEY_TEA(
        R.drawable.ginger_honey_tea,
        "Ginger Honey Tea",
        "Fresh ginger, honey, lemon, water",
        "Steep sliced ginger in hot water, then add honey and lemon. Serve warm for a soothing winter drink."
    ),
    HOT_SPICED_COCOA(
        R.drawable.hot_spiced_cocoa,
        "Hot Spiced Cocoa",
        "Cocoa powder, milk, cinnamon, nutmeg, sugar",
        "Heat milk with cocoa and spices until smooth and rich. Serve warm with a light sprinkle of cinnamon."
    ),
    APPLE_CINNAMON_BREW(
        R.drawable.apple_cinnamon_brew,
        "Apple Cinnamon Brew",
        "Apple slices, cinnamon sticks, cloves, honey, water",
        "Simmer apples and spices in water to release their flavor. Sweeten lightly and serve warm."
    ),
    WARM_BANANA_OAT_MUFFINS(
        R.drawable.warm_banana_oat_muffins,
        "Warm Banana Oat Muffins",
        "Bananas, oats, flour, eggs, honey, baking powder",
        "Mix mashed bananas with oats and batter ingredients, then bake until golden and soft."
    ),
    CINNAMON_SWIRL_ROLLS(
        R.drawable.cinnamon_swirl_rolls,
        "Cinnamon Swirl Rolls",
        "Flour, cinnamon, butter, sugar, yeast",
        "Roll soft dough with cinnamon sugar filling and bake until fluffy. Serve warm for best flavor."
    ),
    BAKED_APPLE_CRISP(
        R.drawable.baked_apple_crisp,
        "Baked Apple Crisp",
        "Apples, oats, butter, brown sugar, cinnamon",
        "Bake sliced apples topped with a crunchy oat mixture until golden and bubbling."
    )
}