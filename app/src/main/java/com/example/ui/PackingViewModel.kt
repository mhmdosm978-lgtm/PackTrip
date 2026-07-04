package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.PackingItem
import com.example.data.PackingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class FavoriteTrip(
    val country: String,
    val city: String,
    val season: String
)

@OptIn(ExperimentalCoroutinesApi::class)
class PackingViewModel(
    private val repository: PackingRepository,
    private val context: Context
) : ViewModel() {

    private val sharedPrefs = context.getSharedPreferences("picktrip_prefs", Context.MODE_PRIVATE)

    private val _selectedLanguage = MutableStateFlow("ku") 
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedSeason = MutableStateFlow("summer") 
    val selectedSeason: StateFlow<String> = _selectedSeason

    private val _isDarkTheme = MutableStateFlow(true) 
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _onboardingStep = MutableStateFlow("welcome") 
    val onboardingStep: StateFlow<String> = _onboardingStep

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _tripCountry = MutableStateFlow("")
    val tripCountry: StateFlow<String> = _tripCountry

    private val _tripCity = MutableStateFlow("")
    val tripCity: StateFlow<String> = _tripCity

    private val _tripDate = MutableStateFlow("")
    val tripDate: StateFlow<String> = _tripDate

    // Favorites state
    private val _favoriteTrips = MutableStateFlow<List<FavoriteTrip>>(emptyList())
    val favoriteTrips: StateFlow<List<FavoriteTrip>> = _favoriteTrips

    // AI Chat state
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading

    init {
        // Load SharedPreferences
        val savedName = sharedPrefs.getString("user_name", "") ?: ""
        val savedCountry = sharedPrefs.getString("trip_country", "") ?: ""
        val savedCity = sharedPrefs.getString("trip_city", "") ?: ""
        val savedDate = sharedPrefs.getString("trip_date", "") ?: ""
        val savedStep = sharedPrefs.getString("onboarding_step", "welcome") ?: "welcome"
        val savedTheme = sharedPrefs.getBoolean("is_dark_theme", true)
        val savedLang = sharedPrefs.getString("selected_language", "ku") ?: "ku"

        _userName.value = savedName
        _tripCountry.value = savedCountry
        _tripCity.value = savedCity
        _tripDate.value = savedDate
        _selectedLanguage.value = savedLang
        _isDarkTheme.value = savedTheme

        // If registered, go directly to main screen
        if (savedName.isNotEmpty() && savedStep == "main") {
            _onboardingStep.value = "main"
        } else {
            _onboardingStep.value = savedStep
        }

        // Load Favorites from delimited string: "Country|City|Season;Country|City|Season"
        val favsString = sharedPrefs.getString("favorite_trips", "") ?: ""
        if (favsString.isNotEmpty()) {
            val list = favsString.split(";").mapNotNull {
                val parts = it.split("|")
                if (parts.size == 3) {
                    FavoriteTrip(parts[0], parts[1], parts[2])
                } else null
            }
            _favoriteTrips.value = list
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.checkAndPrepopulate()
        }
    }

    fun setOnboardingStep(step: String) {
        _onboardingStep.value = step
        sharedPrefs.edit().putString("onboarding_step", step).apply()
    }

    fun setUserName(name: String) {
        _userName.value = name
        sharedPrefs.edit().putString("user_name", name).apply()
    }

    fun setTripCountry(country: String) {
        _tripCountry.value = country
        sharedPrefs.edit().putString("trip_country", country).apply()
    }

    fun setTripCity(city: String) {
        _tripCity.value = city
        sharedPrefs.edit().putString("trip_city", city).apply()
    }

    fun setTripDate(date: String) {
        _tripDate.value = date
        sharedPrefs.edit().putString("trip_date", date).apply()
    }

    // Expose packing items reactively based on current selected season
    val packingItems: StateFlow<List<PackingItem>> = _selectedSeason
        .flatMapLatest { season ->
            repository.getItemsBySeason(season)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun changeLanguage(lang: String) {
        _selectedLanguage.value = lang
        sharedPrefs.edit().putString("selected_language", lang).apply()
    }

    fun changeSeason(season: String) {
        _selectedSeason.value = season
    }

    fun toggleTheme() {
        val newVal = !_isDarkTheme.value
        _isDarkTheme.value = newVal
        sharedPrefs.edit().putBoolean("is_dark_theme", newVal).apply()
    }

    fun toggleCheckStatus(item: PackingItem) {
        viewModelScope.launch {
            repository.updateCheckStatus(item.id, !item.isChecked)
        }
    }

    fun addCustomItem(name: String, category: String = "other") {
        if (name.isBlank()) return
        viewModelScope.launch {
            val newItem = PackingItem(
                nameEn = name,
                nameKu = name,
                nameAr = name,
                season = _selectedSeason.value,
                isChecked = false,
                isCustom = true,
                category = category
            )
            repository.insertItem(newItem)
        }
    }

    fun addWeatherItem(nameEn: String, nameKu: String, nameAr: String, category: String) {
        viewModelScope.launch {
            val newItem = PackingItem(
                nameEn = nameEn,
                nameKu = nameKu,
                nameAr = nameAr,
                season = _selectedSeason.value,
                isChecked = false,
                isCustom = true,
                category = category
            )
            repository.insertItem(newItem)
        }
    }

    fun addWeatherItems(recommendations: List<WeatherRecommendation>) {
        viewModelScope.launch {
            val itemsToInsert = recommendations.map { rec ->
                PackingItem(
                    nameEn = rec.nameEn,
                    nameKu = rec.nameKu,
                    nameAr = rec.nameAr,
                    season = _selectedSeason.value,
                    isChecked = false,
                    isCustom = true,
                    category = rec.category
                )
            }
            repository.insertItems(itemsToInsert)
        }
    }

    fun applyTemplate(template: PackingTemplate) {
        viewModelScope.launch {
            val itemsToInsert = template.items.map { templateItem ->
                PackingItem(
                    nameEn = templateItem.nameEn,
                    nameKu = templateItem.nameKu,
                    nameAr = templateItem.nameAr,
                    season = _selectedSeason.value,
                    isChecked = false,
                    isCustom = true,
                    category = templateItem.category
                )
            }
            repository.insertItems(itemsToInsert)
        }
    }

    fun deleteItem(item: PackingItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun editItem(item: PackingItem, newName: String, newCategory: String) {
        if (newName.isBlank()) return
        viewModelScope.launch {
            val updatedItem = item.copy(
                nameEn = newName,
                nameKu = newName,
                nameAr = newName,
                category = newCategory
            )
            repository.updateItem(updatedItem)
        }
    }

    fun updateItemPhoto(item: PackingItem, photoUri: String?) {
        viewModelScope.launch {
            val updatedItem = item.copy(photoUri = photoUri)
            repository.updateItem(updatedItem)
        }
    }

    fun resetAllChecks() {
        viewModelScope.launch {
            repository.resetAllChecks()
        }
    }

    fun clearCustomItems() {
        viewModelScope.launch {
            repository.clearCustomItems()
        }
    }

    // Toggle Favorite
    fun toggleFavorite(country: String, city: String, season: String) {
        if (country.isBlank() || city.isBlank()) return
        val currentList = _favoriteTrips.value.toMutableList()
        val existing = currentList.find { it.country.equals(country, true) && it.city.equals(city, true) }
        if (existing != null) {
            currentList.remove(existing)
        } else {
            currentList.add(FavoriteTrip(country, city, season))
        }
        _favoriteTrips.value = currentList

        // Persist to SharedPreferences
        val favsString = currentList.joinToString(";") { "${it.country}|${it.city}|${it.season}" }
        sharedPrefs.edit().putString("favorite_trips", favsString).apply()
    }

    // Send AI chat message
    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(text, true)
        _chatMessages.value = _chatMessages.value + userMsg
        _isChatLoading.value = true

        viewModelScope.launch {
            try {
                val systemPrompt = "You are PickTrip AI, a smart travel assistant. The user's active language is ${_selectedLanguage.value}. Answer questions about travel, packing list, weather, tourist attractions, local rules, traditional foods, currency, timezone, etc. Keep your responses organized, helpful and concise, and ALWAYS respond in the user's active language (${_selectedLanguage.value})."
                val request = GeminiRequest(
                    contents = _chatMessages.value.map { msg ->
                        GeminiContent(
                            parts = listOf(GeminiPart(msg.text)),
                            role = if (msg.isUser) "user" else "model"
                        )
                    },
                    systemInstruction = GeminiContent(
                        parts = listOf(GeminiPart(systemPrompt)),
                        role = "system"
                    )
                )
                val apiKey = BuildConfig.GEMINI_API_KEY
                val response = GeminiClient.api.generateContent(apiKey, request)
                val replyText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No response from AI."
                _chatMessages.value = _chatMessages.value + ChatMessage(replyText, false)
            } catch (e: Exception) {
                _chatMessages.value = _chatMessages.value + ChatMessage(
                    "Error: Could not connect to Gemini AI. Check your internet connection or API Key.",
                    false
                )
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    // Reset chat messages
    fun clearChat() {
        _chatMessages.value = emptyList()
    }
}

class PackingViewModelFactory(
    private val repository: PackingRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PackingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PackingViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
