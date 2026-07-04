package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import coil.compose.AsyncImage
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.widget.Toast
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.R
import com.example.data.PackingItem

data class LanguageStrings(
    val title: String,
    val instruction: String,
    val summer: String,
    val winter: String,
    val shareText: String,
    val placeholderAddItem: String,
    val buttonAdd: String,
    val buttonReset: String,
    val buttonClearCustom: String,
    val searchPlaceholder: String,
    val progressPacked: String,
    val emptyListText: String,
    val shareSubject: String,
    val shareMessage: String,
    val buttonCopy: String,
    val toastCopied: String,
    val dialogShareTitle: String,
    val dialogSharePreview: String,
    val dialogClose: String,
    val catDocuments: String,
    val catClothing: String,
    val catElectronics: String,
    val catToiletries: String,
    val catOther: String,
    val selectCategory: String,
    val dialogEditTitle: String,
    val placeholderEditItem: String,
    val buttonSave: String,
    val buttonCancel: String,
    val deleteItemText: String,
    val templatesTitle: String,
    val templatesSubtitle: String,
    val dialogTemplateTitle: String,
    val dialogTemplateDesc: String,
    val buttonApplyTemplate: String,
    val toastTemplateApplied: String,
    val weatherTitle: String,
    val weatherSubtitle: String,
    val weatherDestLabel: String,
    val weatherDestPlaceholder: String,
    val weatherSelectCond: String,
    val weatherForecastTitle: String,
    val weatherRecommendTitle: String,
    val weatherToday: String,
    val weatherTomorrow: String,
    val weatherDayAfter: String,
    val buttonAddSelected: String,
    val toastAdded: String,
    val addPhotoText: String,
    val removePhotoText: String,
    val photoAddedToast: String,
    val photoRemovedToast: String,
    val buttonSharePdf: String,
    val welcomeTitle: String,
    val welcomeSubtitle: String,
    val buttonStart: String,
    val registerTitle: String,
    val registerPrompt: String,
    val usernamePlaceholder: String,
    val buttonNext: String,
    val tripSetupTitle: String,
    val destinationCountry: String,
    val destinationCity: String,
    val tripDateLabel: String,
    val buttonGenerateList: String,
    val buttonBack: String
)

val translations = mapOf(
    "ku" to LanguageStrings(
        title = "PickTrip",
        instruction = "وەرزەکە هەڵبژێرە بۆ بینینی پێداویستییەکان:",
        summer = "هاوین",
        winter = "زستان",
        shareText = "هاوبەشکردن لەگەڵ هاوڕێیان",
        placeholderAddItem = "پێداویستییەکی نوێ بنووسە...",
        buttonAdd = "زیادکردن",
        buttonReset = "سەرلەنوێ ڕێکخستنەوە",
        buttonClearCustom = "سڕینەوەی دەستکردەکان",
        searchPlaceholder = "گەڕان لە پێداویستییەکان...",
        progressPacked = "ئامادەکراو: %d/%d (%d%%)",
        emptyListText = "هیچ پێداویستییەک نەدۆزرایەوە!",
        shareSubject = "PickTrip",
        shareMessage = "پێشکەوتنی ئامادەکردنی جانتاکەم لە ڕێگەی PickTrip (%s): %d/%d تەواو بووە! تۆش ئەپەکە دابەزێنە بۆ ئامادەکردنی گەشتەکەت.",
        buttonCopy = "کۆپیکردنی دەقەکە",
        toastCopied = "لیستی پێداویستییەکان کۆپی کرا بۆ حافیزە! 📋",
        dialogShareTitle = "هاوبەشکردنی لیستی پێداویستییەکان",
        dialogSharePreview = "پێشبینی دەقی هاوبەشکراو:",
        dialogClose = "داخستن",
        catDocuments = "بەڵگەنامە و پارە 📄",
        catClothing = "جلوبەرگ و پێڵاو 👕",
        catElectronics = "ئەلیکترۆنیات 🔌",
        catToiletries = "پاکوخاوێنی و تەندروستی 🧴",
        catOther = "پێداویستی تر 🎒",
        selectCategory = "جۆر دیاری بکە:",
        dialogEditTitle = "دەستکاریکردنی پێداویستی",
        placeholderEditItem = "ناوی پێداویستی بنووسە...",
        buttonSave = "پاشەکەوتکردن",
        buttonCancel = "پاشگەزبوونەوە",
        deleteItemText = "سڕینەوە",
        templatesTitle = "قاڵبەکانی گەشت",
        templatesSubtitle = "پێداویستییەکان دەستبەجێ بەپێی جۆری گەشتەکەت دابنێ",
        dialogTemplateTitle = "جێبەجێکردنی قاڵب: %s",
        dialogTemplateDesc = "ئەم پێداویستییە ئامادەکراوانە دەخرێنە سەر لیستەکەت:",
        buttonApplyTemplate = "زیادکردن بۆ لیست",
        toastTemplateApplied = "قاڵبەکە بە سەرکەوتوویی جێبەجێ کرا! 🎉",
        weatherTitle = "یاوەری کەشوهەوای گەشت 🌦️",
        weatherSubtitle = "پیشنیارکردنی پێداویستییەکان بەپێی کەشوهەوای شوێنی گەشتەکەت",
        weatherDestLabel = "شار یان شوێنی گەشتەکەت بنووسە:",
        weatherDestPlaceholder = "نموونە: لەندەن، دوبەی، هەولێر...",
        weatherSelectCond = "باری کەشوهەوا دیاری بکە:",
        weatherForecastTitle = "کەشوهەوای ٣ ڕۆژ بۆ %s",
        weatherRecommendTitle = "پێشنیارەکانی گەشت بەپێی کەشوهەوا (بەپێی جۆرەکان):",
        weatherToday = "ئەمڕۆ",
        weatherTomorrow = "سبەی",
        weatherDayAfter = "دوو سبەی",
        buttonAddSelected = "زیادکردنی هەڵبژێردراوەکان یەکبەدواویەک",
        toastAdded = "زیادکرا بۆ جانتاکەت! 🎒",
        addPhotoText = "وێنە زیاد بکە",
        removePhotoText = "وێنە بسڕەوە",
        photoAddedToast = "وێنەکە بە سەرکەوتوویی زیادکرا! 📸",
        photoRemovedToast = "وێنەکە سڕدرایەوە! 🗑️",
        buttonSharePdf = "ناردنی وەک PDF 📄",
        welcomeTitle = "بەخێربێیت بۆ گەشتەکەت 🧳",
        welcomeSubtitle = "ڕێکخستن و پلاندانانی جانتای سەفەرەکەت بە دیزاینێکی بێوێنە و مۆدێرن",
        buttonStart = "دەستپێکردنی گەشت 🚀",
        registerTitle = "خۆتۆمارکردنی گەشتیار 👤",
        registerPrompt = "سەرەتا ناوی بەڕێزت بنووسە بۆ کەسیی کردنی ئەپەکە:",
        usernamePlaceholder = "ناوی گەشتیار...",
        buttonNext = "هەنگاوی دواتر ➡️",
        tripSetupTitle = "زانیارییەکانی گەشت ✈️",
        destinationCountry = "وڵاتی مەبەست:",
        destinationCity = "شاری مەبەست:",
        tripDateLabel = "ڕێکەوتی گەشت:",
        buttonGenerateList = "دروستکردنی لیستەکەم 📝",
        buttonBack = "بگەڕێوە دواوە ⬅️"
    ),
    "en" to LanguageStrings(
        title = "PickTrip",
        instruction = "Choose the season to see the essentials:",
        summer = "Summer",
        winter = "Winter",
        shareText = "Share with Friends",
        placeholderAddItem = "Type a new essential...",
        buttonAdd = "Add Item",
        buttonReset = "Reset Checklist",
        buttonClearCustom = "Clear Custom",
        searchPlaceholder = "Search essentials...",
        progressPacked = "Packed: %d/%d (%d%%)",
        emptyListText = "No essentials found!",
        shareSubject = "PickTrip",
        shareMessage = "My packing progress for my travel (%s): %d/%d items packed! Plan your trip with PickTrip app.",
        buttonCopy = "Copy Summary Text",
        toastCopied = "Packing list copied to Clipboard! 📋",
        dialogShareTitle = "Share Packing List",
        dialogSharePreview = "Formatted Text Preview:",
        dialogClose = "Close",
        catDocuments = "Documents & Cash 📄",
        catClothing = "Clothing & Shoes 👕",
        catElectronics = "Electronics & Gadgets 🔌",
        catToiletries = "Toiletries & Grooming 🧴",
        catOther = "Other Essentials 🎒",
        selectCategory = "Select Category:",
        dialogEditTitle = "Edit Packing Item",
        placeholderEditItem = "Enter item name...",
        buttonSave = "Save",
        buttonCancel = "Cancel",
        deleteItemText = "Delete",
        templatesTitle = "Quick Templates",
        templatesSubtitle = "Populate your list instantly based on your trip type",
        dialogTemplateTitle = "Apply Template: %s",
        dialogTemplateDesc = "This will add these pre-defined items to your active list:",
        buttonApplyTemplate = "Add to List",
        toastTemplateApplied = "Template applied successfully! 🎉",
        weatherTitle = "Travel Weather Companion 🌦️",
        weatherSubtitle = "Get weather-based packing recommendations for your destination",
        weatherDestLabel = "Enter Destination City:",
        weatherDestPlaceholder = "e.g., London, Dubai, Erbil...",
        weatherSelectCond = "Set Weather Condition:",
        weatherForecastTitle = "3-Day Forecast for %s",
        weatherRecommendTitle = "Weather Recommendations (Grouped by Category):",
        weatherToday = "Today",
        weatherTomorrow = "Tomorrow",
        weatherDayAfter = "Day After",
        buttonAddSelected = "Add Selected to Suitcase",
        toastAdded = "Added to your suitcase! 🎒",
        addPhotoText = "Add Photo",
        removePhotoText = "Remove Photo",
        photoAddedToast = "Photo added successfully! 📸",
        photoRemovedToast = "Photo removed! 🗑️",
        buttonSharePdf = "Share as PDF 📄",
        welcomeTitle = "Welcome to your Journey 🧳",
        welcomeSubtitle = "Organize and plan your travel suitcase checklist with a modern, elegant design",
        buttonStart = "Start Journey 🚀",
        registerTitle = "Traveler Profile 👤",
        registerPrompt = "First, enter your name to personalize your suitcase companion:",
        usernamePlaceholder = "Your name...",
        buttonNext = "Next Step ➡️",
        tripSetupTitle = "Trip Details ✈️",
        destinationCountry = "Destination Country:",
        destinationCity = "Destination City:",
        tripDateLabel = "Trip Date:",
        buttonGenerateList = "Generate My List 📝",
        buttonBack = "Go Back ⬅️"
    ),
    "ar" to LanguageStrings(
        title = "PickTrip",
        instruction = "اختر الموسم لرؤية الاحتياجات:",
        summer = "الصيف",
        winter = "الشتاء",
        shareText = "مشاركة مع الأصدقاء",
        placeholderAddItem = "اكتب حاجة جديدة...",
        buttonAdd = "إضافة",
        buttonReset = "إعادة ضبط",
        buttonClearCustom = "مسح المخصص",
        searchPlaceholder = "البحث عن الاحتياجات...",
        progressPacked = "الموضب: %d/%d (%d%%)",
        emptyListText = "لم يتم العثور على أي احتياجات!",
        shareSubject = "PickTrip",
        shareMessage = "تقدم تحضير حقيبتي عبر PickTrip (%s): تم توضيب %d/%d! جرب تطبيق PickTrip لتنظيم رحلتك.",
        buttonCopy = "نسخ النص كلياً",
        toastCopied = "تم نسخ القائمة إلى الحافظة! 📋",
        dialogShareTitle = "مشاركة قائمة الأمتعة",
        dialogSharePreview = "معاينة النص المنسق:",
        dialogClose = "إغلاق",
        catDocuments = "الوثائق والأموال 📄",
        catClothing = "الملابس والأحذية 👕",
        catElectronics = "الإلكترونيات والأجهزة 🔌",
        catToiletries = "مستحضرات التجميل والنظافة 🧴",
        catOther = "احتياجات أخرى 🎒",
        selectCategory = "اختر الفئة:",
        dialogEditTitle = "تعديل الغرض",
        placeholderEditItem = "أدخل اسم الغرض...",
        buttonSave = "حفظ",
        buttonCancel = "إلغاء",
        deleteItemText = "حذف",
        templatesTitle = "القوالب الجاهزة",
        templatesSubtitle = "أضف الأغراض فوراً بناءً على نوع رحلتك",
        dialogTemplateTitle = "تطبيق القالب: %s",
        dialogTemplateDesc = "سيتم إضافة هذه الاحتياجات الجاهزة إلى قائمتك النشطة:",
        buttonApplyTemplate = "إضافة إلى القائمة",
        toastTemplateApplied = "تم تطبيق القالب بنجاح! 🎉",
        weatherTitle = "مرافق طقس السفر 🌦️",
        weatherSubtitle = "احصل على توصيات التعبئة بناءً على طقس وجهتك",
        weatherDestLabel = "أدخل مدينة الوجهة:",
        weatherDestPlaceholder = "مثال: لندن، دبي، أربيل...",
        weatherSelectCond = "اختر حالة الطقس للوجهة:",
        weatherForecastTitle = "توقعات الطقس لـ ٣ أيام لـ %s",
        weatherRecommendTitle = "اقتراحات الطقس (مجمعة حسب الفئة):",
        weatherToday = "اليوم",
        weatherTomorrow = "غداً",
        weatherDayAfter = "بعد غد",
        buttonAddSelected = "إضافة المحدد إلى الحقيبة",
        toastAdded = "تمت الإضافة إلى حقيبتك! 🎒",
        addPhotoText = "إضافة صورة",
        removePhotoText = "إزالة الصورة",
        photoAddedToast = "تم إضافة الصورة بنجاح! 📸",
        photoRemovedToast = "تم إزالة الصورة! 🗑️",
        buttonSharePdf = "مشاركة كـ PDF 📄",
        welcomeTitle = "مرحباً بك في رحلتك 🧳",
        welcomeSubtitle = "قم بتنظيم وتخطيط قائمة أمتعة السفر الخاصة بك بتصميم عصري وأنيق",
        buttonStart = "ابدأ الرحلة 🚀",
        registerTitle = "ملف المسافر 👤",
        registerPrompt = "أولاً، أدخل اسمك لتخصيص رفيق حقيبتك الشخصي:",
        usernamePlaceholder = "اسمك الكريم...",
        buttonNext = "الخطوة التالية ➡️",
        tripSetupTitle = "تفاصيل الرحلة ✈️",
        destinationCountry = "بلد الوجهة:",
        destinationCity = "مدينة الوجهة:",
        tripDateLabel = "تاريخ الرحلة:",
        buttonGenerateList = "إنشاء قائمتي 📝",
        buttonBack = "الرجوع ⬅️"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackingApp(
    viewModel: PackingViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val currentLang by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val currentSeason by viewModel.selectedSeason.collectAsStateWithLifecycle()
    val packingItems by viewModel.packingItems.collectAsStateWithLifecycle()
    val isDark by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2200)
        showSplash = false
    }

    val stringData = translations[currentLang] ?: translations["ku"]!!

    // Determine layout direction dynamically based on active language
    val layoutDirection = if (currentLang == "en") LayoutDirection.Ltr else LayoutDirection.Rtl

    var customItemText by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var showShareDialog by remember { mutableStateOf(false) }
    var selectedCategoryForCustom by remember { mutableStateOf("other") }
    var editingItem by remember { mutableStateOf<PackingItem?>(null) }
    var editItemText by remember { mutableStateOf("") }
    var editItemCategory by remember { mutableStateOf("other") }
    var selectedTemplate by remember { mutableStateOf<PackingTemplate?>(null) }
    var weatherCity by remember { mutableStateOf("Erbil") }
    var selectedWeatherCondition by remember { mutableStateOf(WeatherCondition.SUNNY) }
    var weatherBaseTemp by remember { mutableStateOf(38) }

    var currentTab by remember { mutableStateOf("main_list") }
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var showContactDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    var feedbackText by remember { mutableStateOf("") }


    // Filter items based on search query
    val filteredItems = remember(packingItems, searchQuery) {
        if (searchQuery.isBlank()) {
            packingItems
        } else {
            packingItems.filter {
                it.nameEn.contains(searchQuery, ignoreCase = true) ||
                        it.nameKu.contains(searchQuery, ignoreCase = true) ||
                        it.nameAr.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val groupedItems = remember(filteredItems) {
        filteredItems.groupBy { it.category }
    }

    // Stats calculations
    val totalCount = packingItems.size
    val packedCount = packingItems.count { it.isChecked }
    val progressPercent = if (totalCount > 0) (packedCount * 100) / totalCount else 0

    if (showSplash) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                // Beautiful 3D App Icon
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon_1783022800161),
                    contentDescription = "PickTrip Logo",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(32.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(28.dp))
                
                Text(
                    text = "PICKTRIP",
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    letterSpacing = 2.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = "STAY ORGANIZED • TRAVEL LIGHT",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    letterSpacing = 3.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Beautiful loading bar
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(130.dp)
                        .height(4.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Developer credit block
                Card(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "DESIGN & DEVELOPMENT",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Makeing and desing by muhammed osman",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    } else {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = modifier.fillMaxSize()
            ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {
                // Collect onboarding state
                val onboardingStep by viewModel.onboardingStep.collectAsStateWithLifecycle()
                val userName by viewModel.userName.collectAsStateWithLifecycle()
                val tripCountry by viewModel.tripCountry.collectAsStateWithLifecycle()
                val tripCity by viewModel.tripCity.collectAsStateWithLifecycle()
                val tripDate by viewModel.tripDate.collectAsStateWithLifecycle()

                // Common Sticky Top Bar (Language Switcher & Theme Toggle & Logo)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                ) {
                    // Language Switcher & Theme Toggle Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val languages = listOf(
                            "en" to "EN",
                            "ku" to "KU",
                            "ar" to "AR"
                        )
                        languages.forEach { (code, label) ->
                            val isSelected = currentLang == code
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    .clickable { viewModel.changeLanguage(code) }
                                    .testTag("lang_btn_$code")
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // Dynamic Light/Dark Theme Toggle Icon Button
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .clickable { viewModel.toggleTheme() }
                                .testTag("theme_toggle_btn")
                        ) {
                            Text(
                                text = if (isDark) "☀️" else "🌙",
                                fontSize = 18.sp
                            )
                        }
                    }

                    // Tiny App Icon Logo
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon_1783022800161),
                        contentDescription = "PickTrip Mini Logo",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                if (onboardingStep != "main") {
                    when (onboardingStep) {
                        "welcome" -> {
                            WelcomeScreen(
                                stringData = stringData,
                                onStart = { viewModel.setOnboardingStep("register") }
                            )
                        }
                        "register" -> {
                            RegisterScreen(
                                stringData = stringData,
                                currentName = userName,
                                onNext = { name ->
                                    viewModel.setUserName(name)
                                    viewModel.setOnboardingStep("trip_setup")
                                },
                                onBack = { viewModel.setOnboardingStep("welcome") }
                            )
                        }
                        "trip_setup" -> {
                            TripSetupScreen(
                                stringData = stringData,
                                currentCountry = tripCountry,
                                currentCity = tripCity,
                                currentDate = tripDate,
                                currentSeason = currentSeason,
                                onSeasonSelected = { viewModel.changeSeason(it) },
                                onGenerateList = { country, city, date ->
                                    viewModel.setTripCountry(country)
                                    viewModel.setTripCity(city)
                                    viewModel.setTripDate(date)
                                    viewModel.setOnboardingStep("main")
                                },
                                onBack = { viewModel.setOnboardingStep("register") }
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        // Personalized Trip Welcome & Card
                        item {
                            Card(
                                shape = RoundedCornerShape(24.dp),
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        val greetingText = when (currentLang) {
                                            "en" -> "Hello, $userName! 👋"
                                            "ar" -> "مرحباً، $userName! 👋"
                                            else -> "سڵاو، $userName! 👋"
                                        }
                                        Text(
                                            text = greetingText,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        
                                        // Small Edit Button
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                                .clickable { viewModel.setOnboardingStep("trip_setup") }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit Trip Details",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    
                                    Spacer(modifier = Modifier.height(10.dp))
                                    
                                    val destinationLabel = "$tripCity, $tripCountry"
                                    val textToShow = if (currentLang == "en") {
                                        "Your trip to $destinationLabel on $tripDate is ready! Pack your essentials below."
                                    } else if (currentLang == "ar") {
                                        "رحلتك إلى $destinationLabel في $tripDate جاهزة! جهّز أمتعتك أدناه."
                                    } else {
                                        "گەشتەکەت بۆ $destinationLabel لە ڕێکەوتی $tripDate ئامادەیە! پێداویستییەکانت لە خوارەوە دابنێ."
                                    }
                                    Text(
                                        text = textToShow,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }

                    // 2. Styled Header Section
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = stringData.title,
                                fontWeight = FontWeight.Black,
                                fontSize = 38.sp,
                                lineHeight = 42.sp,
                                letterSpacing = (-1).sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "MY TRAVEL LIST",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                letterSpacing = 3.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // 3. Season Selector (Bold Geometric Cards)
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Summer Card
                            val isSummer = currentSeason == "summer"
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(96.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(if (isSummer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                                    .clickable { viewModel.changeSeason("summer") }
                                    .testTag("season_btn_summer")
                                    .padding(16.dp)
                            ) {
                                // Sun Background icon (Much clearer and more visible!)
                                Text(
                                    text = "☀️",
                                    fontSize = 56.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSummer) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                )
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "SUMMER",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.2.sp,
                                        color = if (isSummer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = stringData.summer + " ☀️",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isSummer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }

                            // Winter Card
                            val isWinter = currentSeason == "winter"
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(96.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(if (isWinter) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                                    .clickable { viewModel.changeSeason("winter") }
                                    .testTag("season_btn_winter")
                                    .padding(16.dp)
                            ) {
                                // Snowflake Background icon (Much clearer and more visible!)
                                Text(
                                    text = "❄️",
                                    fontSize = 56.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isWinter) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                )
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "WINTER",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.2.sp,
                                        color = if (isWinter) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = stringData.winter + " ❄️",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isWinter) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }

                    // 4. Progress bar tracker Card (Clean & Geometric)
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = String.format(stringData.progressPacked, packedCount, totalCount, progressPercent),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "🧳",
                                        fontSize = 18.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { if (totalCount > 0) packedCount.toFloat() / totalCount.toFloat() else 0f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(CircleShape),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                                )
                            }
                        }
                    }

                    // 4.5 Quick Packing Templates Section
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column {
                                Text(
                                    text = stringData.templatesTitle.uppercase(),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    letterSpacing = 1.5.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = stringData.templatesSubtitle,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            }

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("templates_row")
                            ) {
                                items(packingTemplates) { template ->
                                    val templateName = when (currentLang) {
                                        "en" -> template.nameEn
                                        "ar" -> template.nameAr
                                        else -> template.nameKu
                                    }

                                    Card(
                                        onClick = { selectedTemplate = template },
                                        modifier = Modifier
                                            .width(140.dp)
                                            .height(84.dp)
                                            .testTag("template_card_${template.id}"),
                                        shape = RoundedCornerShape(16.dp),
                                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surface
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(12.dp)
                                        ) {
                                            Text(
                                                text = template.icon,
                                                fontSize = 28.sp,
                                                modifier = Modifier.align(Alignment.BottomEnd)
                                            )
                                            Column(
                                                verticalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxHeight()
                                            ) {
                                                Text(
                                                    text = templateName,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    lineHeight = 16.sp
                                                )
                                                Text(
                                                    text = "+ ${template.items.size} " + if (currentLang == "en") "items" else if (currentLang == "ar") "أغراض" else "پێداویستی",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 4.6 Weather Forecast Component
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("weather_companion_card"),
                            shape = RoundedCornerShape(24.dp),
                            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Header Row
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        text = "🌦️",
                                        fontSize = 28.sp
                                    )
                                    Column {
                                        Text(
                                            text = stringData.weatherTitle,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = stringData.weatherSubtitle,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                        )
                                    }
                                }

                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

                                // Destination Input
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = stringData.weatherDestLabel,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    OutlinedTextField(
                                        value = weatherCity,
                                        onValueChange = { weatherCity = it },
                                        placeholder = { Text(stringData.weatherDestPlaceholder, fontSize = 13.sp) },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("weather_city_field"),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                            focusedContainerColor = MaterialTheme.colorScheme.background,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background
                                        )
                                    )
                                }

                                // Preloaded Cities
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        items(preloadedCities) { city ->
                                            val isSelected = weatherCity.trim().equals(city.nameEn, ignoreCase = true) ||
                                                    weatherCity.trim().equals(city.nameKu, ignoreCase = true) ||
                                                    weatherCity.trim().equals(city.nameAr, ignoreCase = true)
                                            val cityName = when (currentLang) {
                                                "en" -> city.nameEn
                                                "ar" -> city.nameAr
                                                else -> city.nameKu
                                            }

                                            Surface(
                                                onClick = {
                                                    weatherCity = cityName
                                                    selectedWeatherCondition = city.defaultCondition
                                                    weatherBaseTemp = city.baseTemp
                                                },
                                                shape = RoundedCornerShape(12.dp),
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                                border = BorderStroke(
                                                    width = 1.dp,
                                                    color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                                ),
                                                modifier = Modifier.testTag("city_chip_${city.nameEn}")
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                                ) {
                                                    Text(text = city.emoji, fontSize = 14.sp)
                                                    Text(
                                                        text = cityName,
                                                        fontSize = 12.sp,
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // Weather Selector Row
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = stringData.weatherSelectCond,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        WeatherCondition.values().forEach { cond ->
                                            val isSelected = selectedWeatherCondition == cond
                                            val label = when (currentLang) {
                                                "en" -> cond.labelEn
                                                "ar" -> cond.labelAr
                                                else -> cond.labelKu
                                            }

                                            Surface(
                                                onClick = { selectedWeatherCondition = cond },
                                                shape = RoundedCornerShape(12.dp),
                                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                                border = BorderStroke(
                                                    width = 1.dp,
                                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                                                ),
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .testTag("weather_cond_${cond.id}")
                                            ) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center,
                                                    modifier = Modifier.padding(vertical = 8.dp)
                                                ) {
                                                    Text(text = cond.icon, fontSize = 20.sp)
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Text(
                                                        text = label,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // 3-Day Forecast Display panel
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                                    ),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = String.format(stringData.weatherForecastTitle, weatherCity.ifBlank { "Destination" }),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        val simulatedForecast = getSimulatedForecast(weatherCity, selectedWeatherCondition, weatherBaseTemp)

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            simulatedForecast.forEachIndexed { index, (dayLabel, temp) ->
                                                val translatedDay = when (index) {
                                                    0 -> stringData.weatherToday
                                                    1 -> stringData.weatherTomorrow
                                                    else -> stringData.weatherDayAfter
                                                }
                                                val dayIcon = if (index == 0) selectedWeatherCondition.icon else {
                                                    when (selectedWeatherCondition) {
                                                        WeatherCondition.SUNNY -> "🌤️"
                                                        WeatherCondition.RAINY -> "🌦️"
                                                        WeatherCondition.SNOWY -> "🏔️"
                                                        WeatherCondition.WINDY -> "🍃"
                                                    }
                                                }

                                                Card(
                                                    modifier = Modifier.weight(1f),
                                                    shape = RoundedCornerShape(12.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = MaterialTheme.colorScheme.background
                                                    ),
                                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(10.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = translatedDay,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                                        )
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        Text(text = dayIcon, fontSize = 22.sp)
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        Text(
                                                            text = "$temp°C",
                                                            fontSize = 13.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = MaterialTheme.colorScheme.primary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                // Recommendations Section
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = stringData.weatherRecommendTitle,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        val currentRecs = weatherRecommendations[selectedWeatherCondition] ?: emptyList()

                                        Button(
                                            onClick = {
                                                viewModel.addWeatherItems(currentRecs)
                                                Toast.makeText(context, stringData.toastTemplateApplied, Toast.LENGTH_SHORT).show()
                                            },
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                                contentColor = MaterialTheme.colorScheme.primary
                                            ),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                            modifier = Modifier
                                                .height(32.dp)
                                                .testTag("add_all_weather_suggestions")
                                        ) {
                                            Text(
                                                text = stringData.buttonAddSelected.split(" ").take(2).joinToString(" "),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                    val currentRecommendations = weatherRecommendations[selectedWeatherCondition] ?: emptyList()
                                    val groupedRecs = currentRecommendations.groupBy { it.category }

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        val categoriesList = listOf(
                                            "documents" to stringData.catDocuments,
                                            "clothing" to stringData.catClothing,
                                            "electronics" to stringData.catElectronics,
                                            "toiletries" to stringData.catToiletries,
                                            "other" to stringData.catOther
                                        )

                                        categoriesList.forEach { (catKey, catLabel) ->
                                            val recsForCategory = groupedRecs[catKey]
                                            if (!recsForCategory.isNullOrEmpty()) {
                                                Card(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    shape = RoundedCornerShape(16.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
                                                    ),
                                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.05f))
                                                ) {
                                                    Column(
                                                        modifier = Modifier.padding(10.dp),
                                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                                    ) {
                                                        Text(
                                                            text = catLabel,
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                                        )

                                                        recsForCategory.forEach { rec ->
                                                            val recName = when (currentLang) {
                                                                "en" -> rec.nameEn
                                                                "ar" -> rec.nameAr
                                                                else -> rec.nameKu
                                                            }

                                                            Row(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Text(
                                                                    text = recName,
                                                                    fontSize = 13.sp,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = MaterialTheme.colorScheme.onSurface
                                                                )

                                                                IconButton(
                                                                    onClick = {
                                                                        viewModel.addWeatherItem(rec.nameEn, rec.nameKu, rec.nameAr, catKey)
                                                                        Toast.makeText(context, "${stringData.toastAdded} ($recName)", Toast.LENGTH_SHORT).show()
                                                                    },
                                                                    modifier = Modifier
                                                                        .size(32.dp)
                                                                        .testTag("add_weather_item_${rec.nameEn}")
                                                                ) {
                                                                    Icon(
                                                                        imageVector = Icons.Default.Add,
                                                                        contentDescription = "Add suggested item",
                                                                        tint = MaterialTheme.colorScheme.primary,
                                                                        modifier = Modifier.size(16.dp)
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 5. Search essentials input
                    item {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text(stringData.searchPlaceholder, fontSize = 14.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("search_field"),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            )
                        )
                    }

                    // 6. Adding custom item section
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = customItemText,
                                    onValueChange = { customItemText = it },
                                    placeholder = { Text(stringData.placeholderAddItem, fontSize = 14.sp) },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("add_item_field"),
                                    shape = RoundedCornerShape(16.dp),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(onDone = {
                                        if (customItemText.isNotBlank()) {
                                            viewModel.addCustomItem(customItemText, selectedCategoryForCustom)
                                            customItemText = ""
                                        }
                                    }),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                                    )
                                )

                                Button(
                                    onClick = {
                                        if (customItemText.isNotBlank()) {
                                            viewModel.addCustomItem(customItemText, selectedCategoryForCustom)
                                            customItemText = ""
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    modifier = Modifier
                                        .height(56.dp)
                                        .testTag("add_item_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(stringData.buttonAdd, fontWeight = FontWeight.Bold)
                                }
                            }

                            // Horizontal Category Selector Chips
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
                            ) {
                                Text(
                                    text = stringData.selectCategory,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                )

                                val categoriesList = listOf(
                                    "documents" to stringData.catDocuments,
                                    "clothing" to stringData.catClothing,
                                    "electronics" to stringData.catElectronics,
                                    "toiletries" to stringData.catToiletries,
                                    "other" to stringData.catOther
                                )

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(categoriesList) { (catKey, catLabel) ->
                                        val isSelected = selectedCategoryForCustom == catKey
                                        Surface(
                                            onClick = { selectedCategoryForCustom = catKey },
                                            shape = RoundedCornerShape(12.dp),
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                            ),
                                            modifier = Modifier.testTag("cat_chip_$catKey")
                                        ) {
                                            Text(
                                                text = catLabel,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Section Heading / Categories Order
                    val categoriesOrder = listOf("documents", "clothing", "electronics", "toiletries", "other")
                    var hasAnyItem = false

                    categoriesOrder.forEach { categoryKey ->
                        val itemsInCat = groupedItems[categoryKey] ?: emptyList()
                        if (itemsInCat.isNotEmpty()) {
                            hasAnyItem = true
                            
                            // Category Header Item
                            item(key = "header_$categoryKey") {
                                val catTitle = when (categoryKey) {
                                    "documents" -> stringData.catDocuments
                                    "clothing" -> stringData.catClothing
                                    "electronics" -> stringData.catElectronics
                                    "toiletries" -> stringData.catToiletries
                                    else -> stringData.catOther
                                }
                                
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 18.dp, bottom = 8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = catTitle,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            letterSpacing = 0.5.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        
                                        // Count badge
                                        val catPacked = itemsInCat.count { it.isChecked }
                                        val catTotal = itemsInCat.size
                                        Text(
                                            text = "$catPacked/$catTotal",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                            modifier = Modifier
                                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                                .border(0.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                            
                            items(itemsInCat, key = { it.id }) { item ->
                                val displayName = when (currentLang) {
                                    "en" -> item.nameEn
                                    "ar" -> item.nameAr
                                    else -> item.nameKu
                                }

                                AnimatedVisibility(
                                    visible = true,
                                    enter = fadeIn(),
                                    exit = fadeOut() + shrinkVertically(animationSpec = spring())
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                 haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                                 viewModel.toggleCheckStatus(item)
                                             }
                                            .testTag("item_card_${item.id}"),
                                        shape = RoundedCornerShape(20.dp),
                                        border = BorderStroke(
                                            width = if (item.isChecked) 1.dp else 1.5.dp,
                                            color = if (item.isChecked) MaterialTheme.colorScheme.outline.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary
                                        ),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (item.isChecked) MaterialTheme.colorScheme.surface.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                                        )
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 14.dp)
                                        ) {
                                            // Stark Geometric Checkbox Custom Component
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(6.dp))
                                                    .clickable {
                                                 haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                                 viewModel.toggleCheckStatus(item)
                                             }
                                                    .testTag("checkbox_${item.id}")
                                            ) {
                                                if (item.isChecked) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(12.dp)
                                                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(14.dp))

                                            Text(
                                                text = displayName,
                                                fontSize = 16.sp,
                                                fontWeight = if (item.isChecked) FontWeight.Normal else FontWeight.SemiBold,
                                                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                                color = if (item.isChecked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.weight(1f)
                                            )

                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                             ) {
                                                 // ✏️ Edit Button


                                                 IconButton(
                                                     onClick = {
                                                         editingItem = item
                                                         editItemText = displayName
                                                         editItemCategory = item.category
                                                     },
                                                     modifier = Modifier
                                                         .size(36.dp)
                                                         .testTag("edit_item_${item.id}")
                                                 ) {
                                                     Icon(
                                                         imageVector = Icons.Default.Edit,
                                                         contentDescription = "Edit Item",
                                                         tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                                         modifier = Modifier.size(18.dp)
                                                     )
                                                 }

                                                 // 🗑️ Delete Button
                                                 IconButton(
                                                     onClick = { viewModel.deleteItem(item) },
                                                     modifier = Modifier
                                                         .size(36.dp)
                                                         .testTag("delete_item_${item.id}")
                                                 ) {
                                                     Icon(
                                                         imageVector = Icons.Default.Delete,
                                                         contentDescription = "Delete Item",
                                                         tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                                         modifier = Modifier.size(18.dp)
                                                     )
                                                 }
                                             }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!hasAnyItem) {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp)
                            ) {
                                Text(
                                    text = stringData.emptyListText,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.outline,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Spacer to make list scrollable nicely before actions
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // 8. Persistent Footer actions (Reset, Clear, Share)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.resetAllChecks() },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)
                                        .testTag("reset_checklist_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset"
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringData.buttonReset,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.clearCustomItems() },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)
                                        .testTag("clear_custom_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear Custom"
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringData.buttonClearCustom,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Big Share CTA Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        val shareTextLabel = when (currentLang) {
                            "en" -> "Share Text 📤"
                            "ar" -> "مشاركة نص 📤"
                            else -> "ناردنی دەق 📤"
                        }
                        val sharePdfLabel = when (currentLang) {
                            "en" -> "Share PDF 📄"
                            "ar" -> "مشاركة PDF 📄"
                            else -> "ناردنی PDF 📄"
                        }

                        // Share Text Button
                        Button(
                            onClick = {
                                val seasonLabel = if (currentSeason == "summer") stringData.summer else stringData.winter
                                val summaryText = generatePackingSummary(
                                    lang = currentLang,
                                    seasonLabel = seasonLabel,
                                    packedCount = packedCount,
                                    totalCount = totalCount,
                                    progressPercent = progressPercent,
                                    items = packingItems,
                                    stringData = stringData
                                )
                                shareTravelProgress(context, stringData.shareSubject, summaryText)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                                .testTag("share_text_button")
                        ) {
                            Text(
                                text = shareTextLabel,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Share PDF Button
                        Button(
                            onClick = {
                                val seasonLabel = if (currentSeason == "summer") stringData.summer else stringData.winter
                                sharePackingListAsPdf(
                                    context = context,
                                    lang = currentLang,
                                    seasonLabel = seasonLabel,
                                    packedCount = packedCount,
                                    totalCount = totalCount,
                                    progressPercent = progressPercent,
                                    items = packingItems,
                                    stringData = stringData,
                                    userName = userName,
                                    tripCountry = tripCountry,
                                    tripCity = tripCity,
                                    tripDate = tripDate
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                                .testTag("share_pdf_button")
                        ) {
                            Text(
                                text = sharePdfLabel,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // 9. Bottom Decorative Navigation Accent from "Geometric Balance" design HTML
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 4.dp)
                            .padding(horizontal = 24.dp)
                    ) {
                        // Dot active item
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.alpha(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            )
                            Text(
                                text = "LIST",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Dot items (inactive with 0.3 opacity)
                        val menuItems = listOf("TRIPS", "USER", "INFO")
                        menuItems.forEach { itemLabel ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.alpha(0.3f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                                )
                                Text(
                                    text = itemLabel,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
                } // closes the else block for onboardingStep == "main"
            }
        }
    }



    if (editingItem != null) {
        val item = editingItem!!
        AlertDialog(
            onDismissRequest = { editingItem = null },
            title = {
                Text(
                    text = stringData.dialogEditTitle,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = editItemText,
                        onValueChange = { editItemText = it },
                        placeholder = { Text(stringData.placeholderEditItem, fontSize = 14.sp) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_item_field"),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    // Category Selector inside edit dialog
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringData.selectCategory,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        )

                        val categoriesList = listOf(
                            "documents" to stringData.catDocuments,
                            "clothing" to stringData.catClothing,
                            "electronics" to stringData.catElectronics,
                            "toiletries" to stringData.catToiletries,
                            "other" to stringData.catOther
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(categoriesList) { (catKey, catLabel) ->
                                val isSelected = editItemCategory == catKey
                                Surface(
                                    onClick = { editItemCategory = catKey },
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                                    ),
                                    modifier = Modifier.testTag("edit_cat_chip_$catKey")
                                ) {
                                    Text(
                                        text = catLabel,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = { editingItem = null },
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("cancel_edit_button")
                    ) {
                        Text(
                            text = stringData.buttonCancel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Save Button
                    Button(
                        onClick = {
                            if (editItemText.isNotBlank()) {
                                viewModel.editItem(item, editItemText, editItemCategory)
                                editingItem = null
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1.5f)
                            .height(48.dp)
                            .testTag("save_edit_button")
                    ) {
                        Text(
                            text = stringData.buttonSave,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    if (selectedTemplate != null) {
        val template = selectedTemplate!!
        val templateTranslatedName = when (currentLang) {
            "en" -> template.nameEn
            "ar" -> template.nameAr
            else -> template.nameKu
        }
        AlertDialog(
            onDismissRequest = { selectedTemplate = null },
            title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(template.icon, fontSize = 24.sp)
                    Text(
                        text = String.format(stringData.dialogTemplateTitle, templateTranslatedName),
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringData.dialogTemplateDesc,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Card with list of items
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(template.items) { item ->
                                val itemTranslatedName = when (currentLang) {
                                    "en" -> item.nameEn
                                    "ar" -> item.nameAr
                                    else -> item.nameKu
                                }
                                val categoryIcon = when (item.category) {
                                    "documents" -> "📄"
                                    "clothing" -> "👕"
                                    "electronics" -> "🔌"
                                    "toiletries" -> "🧴"
                                    else -> "🎒"
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = categoryIcon, fontSize = 16.sp)
                                    Text(
                                        text = itemTranslatedName,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = { selectedTemplate = null },
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("cancel_template_button")
                    ) {
                        Text(
                            text = stringData.buttonCancel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Apply Button
                    Button(
                        onClick = {
                            viewModel.applyTemplate(template)
                            selectedTemplate = null
                            Toast.makeText(context, stringData.toastTemplateApplied, Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1.5f)
                            .height(48.dp)
                            .testTag("apply_template_button")
                    ) {
                        Text(
                            text = stringData.buttonApplyTemplate,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surface
        )
    }


}
}

private fun generatePackingSummary(
    lang: String,
    seasonLabel: String,
    packedCount: Int,
    totalCount: Int,
    progressPercent: Int,
    items: List<PackingItem>,
    stringData: LanguageStrings
): String {
    val sb = java.lang.StringBuilder()
    val isEnglish = lang == "en"
    val isArabic = lang == "ar"
    
    if (isEnglish) {
        sb.append("🧳 *PickTrip Checklist*\n")
        sb.append("Season: $seasonLabel\n")
        sb.append("Progress: $packedCount/$totalCount ($progressPercent%)\n\n")
    } else if (isArabic) {
        sb.append("🧳 *قائمة أمتعتي من PickTrip*\n")
        sb.append("الموسم: $seasonLabel\n")
        sb.append("التقدم: $packedCount/$totalCount ($progressPercent%)\n\n")
    } else {
        sb.append("🧳 *لیستی پێداویستییەکانم لە PickTrip*\n")
        sb.append("وەرزی گەشت: $seasonLabel\n")
        sb.append("ڕێژەی ئامادەکردنی جانتا: $packedCount/$totalCount ($progressPercent%)\n\n")
    }
    
    val categoriesOrder = listOf("documents", "clothing", "electronics", "toiletries", "other")
    
    categoriesOrder.forEach { catKey ->
        val catItems = items.filter { it.category == catKey }
        if (catItems.isNotEmpty()) {
            val catLabel = when (catKey) {
                "documents" -> stringData.catDocuments
                "clothing" -> stringData.catClothing
                "electronics" -> stringData.catElectronics
                "toiletries" -> stringData.catToiletries
                else -> stringData.catOther
            }
            sb.append("▪️ *${catLabel.uppercase()}*:\n")
            
            catItems.forEach { item ->
                val name = when (lang) {
                    "en" -> item.nameEn
                    "ar" -> item.nameAr
                    else -> item.nameKu
                }
                val checkSymbol = if (item.isChecked) "✅" else "⏳"
                sb.append("  $checkSymbol $name\n")
            }
            sb.append("\n")
        }
    }
    
    if (isEnglish) {
        sb.append("Plan your trip with PickTrip!")
    } else if (isArabic) {
        sb.append("نظّم رحلتك مع تطبيق PickTrip!")
    } else {
        sb.append("گەشتەکەت ڕێکبخە بە ئەپی PickTrip!")
    }
    
    return sb.toString()
}

private fun shareTravelProgress(context: Context, subject: String, message: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

private fun sharePackingListAsPdf(
    context: Context,
    lang: String,
    seasonLabel: String,
    packedCount: Int,
    totalCount: Int,
    progressPercent: Int,
    items: List<PackingItem>,
    stringData: LanguageStrings,
    userName: String,
    tripCountry: String,
    tripCity: String,
    tripDate: String
) {
    try {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        
        val paint = Paint().apply {
            isAntiAlias = true
        }
        
        // 1. Draw Background
        paint.color = android.graphics.Color.WHITE
        canvas.drawRect(0f, 0f, 595f, 842f, paint)
        
        // 2. Header Box (Decorative Premium Header)
        paint.color = android.graphics.Color.rgb(18, 18, 18) // Deep Jet Black
        canvas.drawRect(30f, 30f, 565f, 130f, paint)
        
        // Title text in white
        paint.color = android.graphics.Color.WHITE
        paint.textSize = 24f
        paint.isFakeBoldText = true
        val titleText = when (lang) {
            "en" -> "PICKTRIP"
            "ar" -> "PickTrip"
            else -> "PickTrip"
        }
        canvas.drawText(titleText, 50f, 75f, paint)
        
        // Subtitle text
        paint.textSize = 10f
        paint.isFakeBoldText = false
        val subtitleText = "DESIGN & DEVELOPMENT BY MUHAMMED OSMAN • STAY ORGANIZED"
        canvas.drawText(subtitleText, 50f, 105f, paint)
        
        // 3. Metadata Section
        paint.color = android.graphics.Color.BLACK
        paint.textSize = 12f
        paint.isFakeBoldText = true
        
        var yPosition = 155f
        
        // Traveler & Destination details
        val travelerLabel = if (lang == "en") "Traveler: $userName" else if (lang == "ar") "المسافر: $userName" else "گەشتیار: $userName"
        val destLabel = if (lang == "en") "Destination: $tripCity, $tripCountry" else if (lang == "ar") "الوجهة: $tripCity، $tripCountry" else "شوێنی مەبەست: $tripCity، $tripCountry"
        val dateLabel = if (lang == "en") "Trip Date: $tripDate" else if (lang == "ar") "تاريخ الرحلة: $tripDate" else "ڕێکەوتی گەشت: $tripDate"
        
        canvas.drawText(travelerLabel, 40f, yPosition, paint)
        yPosition += 18f
        canvas.drawText(destLabel, 40f, yPosition, paint)
        yPosition += 18f
        canvas.drawText(dateLabel, 40f, yPosition, paint)
        yPosition += 18f
        
        val seasonText = if (lang == "en") "Season: $seasonLabel" else if (lang == "ar") "الموسم: $seasonLabel" else "وەرزی گەشت: $seasonLabel"
        val progressText = if (lang == "en") "Progress: $packedCount / $totalCount ($progressPercent%)" else if (lang == "ar") "التقدم: $packedCount / $totalCount ($progressPercent%)" else "ڕێژەی ئامادەکردنی جانتا: $packedCount / $totalCount ($progressPercent%)"
        
        canvas.drawText(seasonText, 40f, yPosition, paint)
        yPosition += 18f
        canvas.drawText(progressText, 40f, yPosition, paint)
        yPosition += 22f
        
        // Draw Divider
        paint.strokeWidth = 1f
        paint.color = android.graphics.Color.LTGRAY
        canvas.drawLine(40f, yPosition, 555f, yPosition, paint)
        yPosition += 25f
        
        // 4. Render Categorized Items
        val categoriesOrder = listOf("documents", "clothing", "electronics", "toiletries", "other")
        
        categoriesOrder.forEach { catKey ->
            val catItems = items.filter { it.category == catKey }
            if (catItems.isNotEmpty()) {
                // Category Title
                val catLabel = when (catKey) {
                    "documents" -> stringData.catDocuments
                    "clothing" -> stringData.catClothing
                    "electronics" -> stringData.catElectronics
                    "toiletries" -> stringData.catToiletries
                    else -> stringData.catOther
                }
                
                if (yPosition > 800f) return@forEach
                
                paint.color = android.graphics.Color.rgb(40, 40, 40)
                paint.textSize = 13f
                paint.isFakeBoldText = true
                canvas.drawText(catLabel.uppercase(), 40f, yPosition, paint)
                yPosition += 18f
                
                catItems.forEach { item ->
                    if (yPosition > 810f) return@forEach
                    
                    val name = when (lang) {
                        "en" -> item.nameEn
                        "ar" -> item.nameAr
                        else -> item.nameKu
                    }
                    
                    // Draw checkbox box
                    paint.color = android.graphics.Color.BLACK
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 1.5f
                    canvas.drawRect(50f, yPosition - 11f, 62f, yPosition + 1f, paint)
                    
                    // If checked, draw a small checkmark solid square
                    paint.style = Paint.Style.FILL
                    if (item.isChecked) {
                        paint.color = android.graphics.Color.rgb(0, 150, 0) // Green check
                        canvas.drawRect(52f, yPosition - 9f, 60f, yPosition - 1f, paint)
                    }
                    
                    // Draw item text
                    paint.color = if (item.isChecked) android.graphics.Color.GRAY else android.graphics.Color.BLACK
                    paint.textSize = 11f
                    paint.isFakeBoldText = false
                    canvas.drawText(name, 75f, yPosition, paint)
                    
                    yPosition += 18f
                }
                yPosition += 10f
            }
        }
        
        // Footer signature
        paint.color = android.graphics.Color.GRAY
        paint.textSize = 9f
        paint.isFakeBoldText = false
        val footerText = if (lang == "en") "Generated with PickTrip App." else if (lang == "ar") "تم الإنشاء بواسطة تطبيق PickTrip." else "دروستکراوە لە ڕێگەی ئەپی PickTrip."
        canvas.drawText(footerText, 40f, 825f, paint)
        
        pdfDocument.finishPage(page)
        
        // Save to cache directory
        val cacheDir = context.cacheDir
        val pdfFile = File(cacheDir, "Suitcase_Packing_List.pdf")
        if (pdfFile.exists()) {
            pdfFile.delete()
        }
        
        val fos = FileOutputStream(pdfFile)
        pdfDocument.writeTo(fos)
        fos.close()
        pdfDocument.close()
        
        // Share via FileProvider
        val authority = "${context.packageName}.fileprovider"
        val pdfUri = FileProvider.getUriForFile(context, authority, pdfFile)
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, pdfUri)
            type = "application/pdf"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share PDF Packing List"))
        
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error generating PDF: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun VerticalSuitcaseLogo(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 100.dp,
    backgroundColor: Color = Color.White,
    suitcaseColor: Color = Color.Black,
    ridgeColor: Color = Color.White.copy(alpha = 0.4f)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.2f))
            .background(backgroundColor)
            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(size * 0.2f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight().padding(vertical = size * 0.08f)
        ) {
            // Handle
            Box(
                modifier = Modifier
                    .width(size * 0.28f)
                    .height(size * 0.09f)
                    .border(4.dp, suitcaseColor, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
            )
            Spacer(modifier = Modifier.height(2.dp))
            // Suitcase body
            Box(
                modifier = Modifier
                    .width(size * 0.46f)
                    .weight(1f)
                    .background(suitcaseColor, RoundedCornerShape(size * 0.08f))
                    .padding(vertical = size * 0.06f),
                contentAlignment = Alignment.Center
            ) {
                // Horizontal grooves/lines on the suitcase body
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .width(size * 0.36f)
                                .height(2.5.dp)
                                .background(ridgeColor, CircleShape)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            // Wheels
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(size * 0.40f)
            ) {
                Box(
                    modifier = Modifier
                        .size(size * 0.11f)
                        .background(suitcaseColor, CircleShape)
                   )
                   Box(
                       modifier = Modifier
                           .size(size * 0.11f)
                           .background(suitcaseColor, CircleShape)
                   )
               }
           }
       }
   }

data class CityTouristInfo(
    val currencyEn: String,
    val currencyKu: String,
    val currencyAr: String,
    val languageEn: String,
    val languageKu: String,
    val languageAr: String,
    val attractionsEn: List<String>,
    val attractionsKu: List<String>,
    val attractionsAr: List<String>,
    val descriptionEn: String,
    val descriptionKu: String,
    val descriptionAr: String
)

fun getCityTouristInfo(cityName: String): CityTouristInfo {
    return when (cityName.lowercase().trim()) {
        "erbil", "هەولێر", "أربيل" -> CityTouristInfo(
            currencyEn = "Iraqi Dinar (IQD)",
            currencyKu = "دیناری عێراقی (IQD)",
            currencyAr = "دينار عراقي (IQD)",
            languageEn = "Kurdish, Arabic",
            languageKu = "کوردی، عەرەبی",
            languageAr = "الكردية، العربية",
            attractionsEn = listOf("Erbil Citadel 🏰", "Sami Abdulrahman Park 🌳", "Korek Mountain Cable Car 🚠"),
            attractionsKu = listOf("قەڵای هەولێر 🏰", "پارکی سامی عەبدولڕەحمان 🌳", "تەلەفریکی چیای کۆڕەک 🚠"),
            attractionsAr = listOf("قلعة أربيل 🏰", "متنزه سامي عبدالرحمن 🌳", "تلفريك جبل كورك 🚠"),
            descriptionEn = "One of the oldest continuously inhabited cities in the world, famous for its historic Citadel.",
            descriptionKu = "یەکێک لە کۆنترین شارە ئاوەدانەکانی جیھان، ناسراوە بە قەڵا مێژووییەکەی.",
            descriptionAr = "واحدة من أقدم المدن المأهولة باستمرار في العالم، وتشتهر بقلعتها التاريخية."
        )
        "sulaymaniyah", "سلێمانی", "السليمانية" -> CityTouristInfo(
            currencyEn = "Iraqi Dinar (IQD)",
            currencyKu = "دیناری عێراقی (IQD)",
            currencyAr = "دينار عراقي (IQD)",
            languageEn = "Kurdish, Arabic",
            languageKu = "کوردی، عەرەبی",
            languageAr = "الكردية، العربية",
            attractionsEn = listOf("Goizha Mountain Viewpoint ⛰️", "Sarchinar Tourist Resort 🏞️", "Chavi Land Amusement Park 🎡"),
            attractionsKu = listOf("لووتکەی چیای گۆیژە ⛰️", "سەیرانگای سەرچنار 🏞️", "چاڤی لاند 🎡"),
            attractionsAr = listOf("قمة جبل كويجة ⛰️", "منتجع سرجنار السياحي 🏞️", "مدينة ألعاب چافي لاند 🎡"),
            descriptionEn = "The cultural capital of Kurdistan, surrounded by beautiful mountains and parks.",
            descriptionKu = "پایتەختی ڕۆشنبیریی کوردستان، دەورەدراوە بە چیا و پارکە قەشەنگەکان.",
            descriptionAr = "العاصمة الثقافية لكردستان، محاطة بالجبال الجميلة والحدائق."
        )
        "duhok", "دهۆک", "دهوك" -> CityTouristInfo(
            currencyEn = "Iraqi Dinar (IQD)",
            currencyKu = "دیناری عێراقی (IQD)",
            currencyAr = "دينار عراقي (IQD)",
            languageEn = "Kurdish, Arabic",
            languageKu = "کوردی، عەرەبی",
            languageAr = "الكردية، العربية",
            attractionsEn = listOf("Duhok Dam & Lake 🌊", "Charsteen Cave ⛰️", "Dream City Theme Park 🎡"),
            attractionsKu = listOf("بەنداوی دهۆک و دەریاچەکەی 🌊", "ئەشکەوتی چوارستوون ⛰️", "دریم سیتی 🎡"),
            attractionsAr = listOf("sد دهوك وبحيرته 🌊", "كهف جوارستين ⛰️", "مدينة ألعاب دريم سيتي 🎡"),
            descriptionEn = "A picturesque mountain city known for its beautiful nature, dam, and valleys.",
            descriptionKu = "شارێکی شاخاوی سەرنجڕاکێش کە بە سروشتە جوانەکەی، بەنداوەکەی، و دۆڵەکانی ناسراوە.",
            descriptionAr = "مدينة جبلية خلابة تشتهر بطبيعتها الجميلة وسدها ووديانها."
        )
        "dubai", "دوبەی", "دبي" -> CityTouristInfo(
            currencyEn = "UAE Dirham (AED)",
            currencyKu = "دیرهەمی ئیماراتی (AED)",
            currencyAr = "درهم إماراتي (AED)",
            languageEn = "Arabic, English",
            languageKu = "عەرەبی، ئینگلیزی",
            languageAr = "العربية، الإنجليزية",
            attractionsEn = listOf("Burj Khalifa 🏙️", "The Dubai Mall 🛍️", "Palm Jumeirah Island 🏝️"),
            attractionsKu = listOf("بورج خەلیفە 🏙️", "دوبەی مۆڵ 🛍️", "دوورگەی پاڵم جومەیرا 🏝️"),
            attractionsAr = listOf("برج خليفة 🏙️", "دبي مول 🛍️", "جزيرة نخلة جميرا 🏝️"),
            descriptionEn = "A global city of futuristic skyscrapers, luxury shopping, and exciting nightlife.",
            descriptionKu = "شارێکی جیهانییە کە بە تەلارە بەرزە مۆدێرنەکان، بازاڕکردنی ناوازە، و ژیانی ناوازە ناسراوە.",
            descriptionAr = "مدينة عالمية تتميز بناطحات السحاب المستقبلية والتسوق الفاخر والحياة الليلية المثيرة."
        )
        "london", "لەندەن", "لندن" -> CityTouristInfo(
            currencyEn = "British Pound (GBP)",
            currencyKu = "پاوەندی بریتانی (GBP)",
            currencyAr = "جنيه إسترليني (GBP)",
            languageEn = "English",
            languageKu = "ئینگلیزی",
            languageAr = "الإنجليزية",
            attractionsEn = listOf("Big Ben & Parliament 🇬🇧", "The London Eye 🎡", "British Museum 🏛️"),
            attractionsKu = listOf("بیگ بێن و پەرلەمان 🇬🇧", "لەندەن ئای 🎡", "مۆزەخانەی بریتانی 🏛️"),
            attractionsAr = listOf("ساعة بيغ بن والبرلمان 🇬🇧", "عين لندن 🎡", "المتحف البريطاني 🏛️"),
            descriptionEn = "The capital of the UK, rich in royal history, world-class museums, and beautiful parks.",
            descriptionKu = "پایتەختی بریتانیا، دەوڵەمەند بە مێژووی شاهانە، مۆزەخانەی جیهانی و پارکە جوانەکان.",
            descriptionAr = "عاصمة المملكة المتحدة، غنية بالتاريخ الملكي والمتاحف العالمية والحدائق الجميلة."
        )
        "paris", "پاریس", "باريس" -> CityTouristInfo(
            currencyEn = "Euro (EUR)",
            currencyKu = "یۆرۆ (EUR)",
            currencyAr = "يورو (EUR)",
            languageEn = "French",
            languageKu = "فەڕەنسی",
            languageAr = "الفرنسية",
            attractionsEn = listOf("Eiffel Tower 🗼", "Louvre Museum 🏛️", "Arc de Triomphe 🏛️"),
            attractionsKu = listOf("بورجی ئیڤڵ 🗼", "مۆزەخانەی لۆڤەر 🏛️", "کەوانەی سەرکەوتن 🏛️"),
            attractionsAr = listOf("برج إيفل 🗼", "متحف اللوفر 🏛️", "قوس النصر 🏛️"),
            descriptionEn = "The global center for art, fashion, gastronomy, and culture, known as the City of Light.",
            descriptionKu = "ناوەندی جیهانیی هونەر، مۆد، خۆراک، و کولتوور، ناسراو بە شاری ڕووناکی.",
            descriptionAr = "المركز العالمي للفنون والموضة والثقافة، والمعروفة باسم مدينة النور."
        )
        "istanbul", "ئەستەنبوڵ", "إسطنبول" -> CityTouristInfo(
            currencyEn = "Turkish Lira (TRY)",
            currencyKu = "لیرەی تورکی (TRY)",
            currencyAr = "ليرة تركية (TRY)",
            languageEn = "Turkish",
            languageKu = "تورکی",
            languageAr = "التركية",
            attractionsEn = listOf("Hagia Sophia Mosque 🕌", "The Blue Mosque 🕌", "Grand Bazaar 🛍️"),
            attractionsKu = listOf("مزگەوتی ئایا سۆفیا 🕌", "مزگەوتی شین 🕌", "بازاڕی گەورە (کاپالی چارشی) 🛍️"),
            attractionsAr = listOf("آيا صوفيا 🕌", "الجامع الأزرق 🕌", "البازار الكبير 🛍️"),
            descriptionEn = "A historic city bridging Europe and Asia, filled with ancient mosques and vibrant culture.",
            descriptionKu = "شارێکی مێژوویی کە ئەوروپا و ئاسیا بەیەکەوە دەبەستێتەوە، پڕ لە مزگەوتی دێرین و کولتووری زیندوو.",
            descriptionAr = "مدينة تاريخية تربط بين أوروبا وآسيا، مليئة بالمساجد القديمة والثقافة النابضة بالحياة."
        )
        else -> CityTouristInfo(
            currencyEn = "Local Currency / US Dollar (USD)",
            currencyKu = "دراوی ناوخۆیی / دۆلاری ئەمریکی (USD)",
            currencyAr = "العملة المحلية / الدولار الأمريكي (USD)",
            languageEn = "English & Local Languages",
            languageKu = "ئینگلیزی و زمانە ناوخۆییەکان",
            languageAr = "الإنجليزية واللغات المحلية",
            attractionsEn = listOf("Local Landmarks 🏰", "Traditional Markets 🛍️", "Nature Parks 🌳"),
            attractionsKu = listOf("شوێنەوارە ناوخۆییەکان 🏰", "بازاڕە کۆنەکان 🛍️", "پارکە سروشتییەکان 🌳"),
            attractionsAr = listOf("المعالم السياحية المحلية 🏰", "الأسواق التقليدية 🛍️", "المنتزهات الطبيعية 🌳"),
            descriptionEn = "A beautiful travel destination filled with culture, hospitality, and scenic sights.",
            descriptionKu = "شوێنێکی گەشتیاری ناوازە کە پڕە لە کولتوور، میواندۆستی، و دیمەنی جوان.",
            descriptionAr = "وجهة سفر جميلة مليئة بالثقافة والضيافة والمناظر الطبيعية الخلابة."
        )
    }
}

@Composable
fun WelcomeScreen(
    stringData: LanguageStrings,
    onStart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Beautiful 3D App Icon
            Image(
                painter = painterResource(id = R.drawable.img_app_icon_1783022800161),
                contentDescription = "PickTrip Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(36.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringData.welcomeTitle,
                fontWeight = FontWeight.Black,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringData.welcomeSubtitle,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .testTag("welcome_start_button")
            ) {
                Text(
                    text = stringData.buttonStart,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Developer Credit
            Card(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "DESIGN & DEVELOPMENT",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Makeing and desing by muhammed osman",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    stringData: LanguageStrings,
    currentName: String,
    onNext: (String) -> Unit,
    onBack: () -> Unit
) {
    var nameState by remember { mutableStateOf(currentName) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = stringData.registerTitle,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringData.registerPrompt,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            
            Spacer(modifier = Modifier.height(36.dp))
            
            OutlinedTextField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = { Text(stringData.usernamePlaceholder) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .testTag("register_name_input")
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                ) {
                    Text(
                        text = stringData.buttonBack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Button(
                    onClick = { if (nameState.isNotBlank()) onNext(nameState) },
                    enabled = nameState.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(58.dp)
                        .testTag("register_next_button")
                ) {
                    Text(
                        text = stringData.buttonNext,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TripSetupScreen(
    stringData: LanguageStrings,
    currentCountry: String,
    currentCity: String,
    currentDate: String,
    currentSeason: String,
    onSeasonSelected: (String) -> Unit,
    onGenerateList: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var countryState by remember { mutableStateOf(currentCountry) }
    var cityState by remember { mutableStateOf(currentCity) }
    var dateState by remember { mutableStateOf(currentDate) }

    val context = LocalContext.current
    val calendar = java.util.Calendar.getInstance()
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                dateState = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        )
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringData.tripSetupTitle,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Country input with Autocomplete Suggestions!
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringData.destinationCountry,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
                OutlinedTextField(
                    value = countryState,
                    onValueChange = { countryState = it },
                    placeholder = { Text("e.g. Iraq, UK, UAE...") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("trip_country_input")
                )

                // Country Suggestions
                val countrySuggestions = remember(countryState) {
                    if (countryState.isBlank()) {
                        preloadedCountries.take(3)
                    } else {
                        preloadedCountries.filter {
                            it.nameEn.contains(countryState, ignoreCase = true) ||
                            it.nameKu.contains(countryState, ignoreCase = true) ||
                            it.nameAr.contains(countryState, ignoreCase = true)
                        }.take(4)
                    }
                }

                if (countrySuggestions.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        countrySuggestions.forEach { country ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                    .clickable { countryState = country.nameEn }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = country.flag, fontSize = 14.sp)
                                    Text(
                                        text = country.nameEn,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // City input with preloaded suggestions based on selected country!
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringData.destinationCity,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
                OutlinedTextField(
                    value = cityState,
                    onValueChange = { cityState = it },
                    placeholder = { Text("e.g. Erbil, London, Dubai...") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("trip_city_input")
                )
                
                // Show 4 click suggestions from preloaded cities based on input or popular!
                val citySuggestions = remember(cityState, countryState) {
                    if (cityState.isBlank()) {
                        val matchingForCountry = preloadedCities.filter { city ->
                            countryState.isNotBlank() && (
                                (countryState.contains("Iraq", ignoreCase = true) || countryState.contains("عێراق", ignoreCase = true) || countryState.contains("العراق", ignoreCase = true)) &&
                                (city.nameEn == "Erbil" || city.nameEn == "Sulaymaniyah" || city.nameEn == "Duhok" || city.nameEn == "Kirkuk")
                            )
                        }
                        if (matchingForCountry.isNotEmpty()) {
                            matchingForCountry.take(4)
                        } else {
                            listOf(
                                DestinationCity("Erbil", "هەولێر", "أربيل", "🏰", WeatherCondition.SUNNY, 38),
                                DestinationCity("Sulaymaniyah", "سلێمانی", "السليمانية", "⛰️", WeatherCondition.SUNNY, 35),
                                DestinationCity("London", "لەندەن", "لندن", "🎡", WeatherCondition.RAINY, 18),
                                DestinationCity("Dubai", "دوبەی", "دبي", "🏙️", WeatherCondition.SUNNY, 41)
                            )
                        }
                    } else {
                        preloadedCities.filter {
                            it.nameEn.contains(cityState, ignoreCase = true) ||
                            it.nameKu.contains(cityState, ignoreCase = true) ||
                            it.nameAr.contains(cityState, ignoreCase = true)
                        }.take(4)
                    }
                }
                
                if (citySuggestions.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        citySuggestions.forEach { city ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                    .clickable { cityState = city.nameEn }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = city.emoji, fontSize = 14.sp)
                                    Text(
                                        text = city.nameEn,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Date Input with Advanced Datepicker Calendar click!
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringData.tripDateLabel,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
                OutlinedTextField(
                    value = dateState,
                    onValueChange = { dateState = it },
                    placeholder = { Text("YYYY-MM-DD (Click to open Calendar)") },
                    singleLine = true,
                    readOnly = true, // Force standard Calendar day, month, year picker
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Text("📅", fontSize = 18.sp)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                        .testTag("trip_date_input")
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Season Selector
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringData.instruction,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val isSummer = currentSeason == "summer"
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSummer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                            .clickable { onSeasonSelected("summer") }
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "☀️ " + stringData.summer,
                            fontWeight = FontWeight.Bold,
                            color = if (isSummer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                    
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (!isSummer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                            .clickable { onSeasonSelected("winter") }
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "❄️ " + stringData.winter,
                            fontWeight = FontWeight.Bold,
                            color = if (!isSummer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(36.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onBack,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp)
                ) {
                    Text(
                        text = stringData.buttonBack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Button(
                    onClick = {
                        if (countryState.isNotBlank() && cityState.isNotBlank()) {
                            onGenerateList(countryState, cityState, dateState)
                        }
                    },
                    enabled = countryState.isNotBlank() && cityState.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .weight(1.5f)
                        .height(58.dp)
                        .testTag("trip_generate_button")
                ) {
                    Text(
                        text = stringData.buttonGenerateList,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
