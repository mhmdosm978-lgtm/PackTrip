package com.example.ui

enum class WeatherCondition(
    val id: String,
    val icon: String,
    val labelEn: String,
    val labelKu: String,
    val labelAr: String
) {
    SUNNY("sunny", "☀️", "Sunny", "خۆش / گەرم", "مشمس"),
    RAINY("rainy", "🌧️", "Rainy", "باراناوی", "ممطر"),
    SNOWY("snowy", "❄️", "Snowy", "بەفراوی", "ثلجي"),
    WINDY("windy", "💨", "Windy", "باکردوو", "عاصف/رياح")
}

data class WeatherRecommendation(
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val category: String
)

data class DestinationCity(
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val emoji: String,
    val defaultCondition: WeatherCondition,
    val baseTemp: Int
)

val preloadedCities = listOf(
    DestinationCity("Erbil", "هەولێر", "أربيل", "🏰", WeatherCondition.SUNNY, 38),
    DestinationCity("Sulaymaniyah", "سلێمانی", "السليمانية", "⛰️", WeatherCondition.SUNNY, 35),
    DestinationCity("Duhok", "دهۆک", "دهوك", "🍇", WeatherCondition.SUNNY, 36),
    DestinationCity("Halabja", "هەڵەبجە", "حلبجة", "🌸", WeatherCondition.SUNNY, 34),
    DestinationCity("Kirkuk", "کەرکووک", "كركوك", "🔥", WeatherCondition.SUNNY, 39),
    DestinationCity("Mosul", "مووسڵ", "الموصل", "🌉", WeatherCondition.SUNNY, 37),
    DestinationCity("Baghdad", "بەغداد", "بغداد", "🌴", WeatherCondition.SUNNY, 44),
    DestinationCity("Basra", "بەسرە", "البصرة", "⚓", WeatherCondition.SUNNY, 46),
    DestinationCity("Amman", "عەممان", "عمان", "🇯🇴", WeatherCondition.SUNNY, 32),
    DestinationCity("Beirut", "بەیرووت", "بيروت", "🇱🇧", WeatherCondition.SUNNY, 29),
    DestinationCity("Mecca", "مەککە", "مكة", "🕋", WeatherCondition.SUNNY, 42),
    DestinationCity("Medina", "مەدینە", "المدينة المنورة", "🕌", WeatherCondition.SUNNY, 43),
    DestinationCity("Dubai", "دوبەی", "دبي", "🏙️", WeatherCondition.SUNNY, 41),
    DestinationCity("London", "لەندەن", "لندن", "🇬🇧", WeatherCondition.RAINY, 15),
    DestinationCity("Paris", "پاریس", "باريس", "🗼", WeatherCondition.WINDY, 18),
    DestinationCity("Rome", "ڕۆما", "روما", "🏛️", WeatherCondition.SUNNY, 26),
    DestinationCity("Barcelona", "بارسێلۆنا", "برشلونة", "🇪🇸", WeatherCondition.SUNNY, 28),
    DestinationCity("Istanbul", "ئەستەنبوڵ", "إسطنبول", "🕌", WeatherCondition.WINDY, 22),
    DestinationCity("Reykjavik", "ڕێکیاڤیک", "ريكيافيك", "🏔️", WeatherCondition.SNOWY, -2),
    DestinationCity("Zurich", "زیورخ", "زيورخ", "🇨🇭", WeatherCondition.RAINY, 14),
    DestinationCity("Berlin", "بەرلین", "برلين", "🐻", WeatherCondition.RAINY, 16),
    DestinationCity("Stockholm", "ستۆکهۆڵم", "ستوكهولم", "❄️", WeatherCondition.RAINY, 12),
    DestinationCity("Tokyo", "تۆکیۆ", "طوكيو", "⛩️", WeatherCondition.RAINY, 20),
    DestinationCity("Seoul", "سیئۆل", "سول", "🇰🇷", WeatherCondition.RAINY, 19),
    DestinationCity("New York", "نیویۆرک", "نيويورك", "🗽", WeatherCondition.WINDY, 23),
    DestinationCity("Toronto", "تۆرۆنتۆ", "تورونتو", "🇨🇦", WeatherCondition.WINDY, 19),
    DestinationCity("Cairo", "قاهیرە", "القاهرة", "🐪", WeatherCondition.SUNNY, 36),
    DestinationCity("Sydney", "سیدنی", "سيدني", "🦘", WeatherCondition.SUNNY, 21),
    DestinationCity("Bali", "بالی", "بالي", "🏝️", WeatherCondition.SUNNY, 30)
)

val weatherRecommendations = mapOf(
    WeatherCondition.SUNNY to listOf(
        WeatherRecommendation("Sunglasses", "چاویلکەی خۆری", "نظارات شمسية", "clothing"),
        WeatherRecommendation("Sun Hat", "کڵاوی خۆر", "قبعة شمسية", "clothing"),
        WeatherRecommendation("Shorts & Swimwear", "شۆرت و جلی مەلەکردن", "شورت وملابس سباحة", "clothing"),
        WeatherRecommendation("Sunscreen Lotion (SPF 50)", "لۆشنی دژەخۆر", "واقي شمس قوي", "toiletries"),
        WeatherRecommendation("Deodorant", "دیۆدۆرانت", "مزيل عرق", "toiletries"),
        WeatherRecommendation("Insulated Water Flask", "مەترەقەی پارێزەری سەرما", "مطرة ماء عازلة للحرارة", "other"),
        WeatherRecommendation("Portable Mini Fan", "باوەشێن یان پانکەی دەستی", "مروحة يد محمولة", "electronics")
    ),
    WeatherCondition.RAINY to listOf(
        WeatherRecommendation("Compact Umbrella", "چەترێکی بچووک", "مظلة صغيرة", "other"),
        WeatherRecommendation("Waterproof Raincoat", "بارانپۆش", "معطف واق من المطر", "clothing"),
        WeatherRecommendation("Waterproof Shoes / Boots", "پێڵاوی دژەئاو", "حذاء مقاوم للماء", "clothing"),
        WeatherRecommendation("Ziploc Bags for Documents", "کیسی پاراستنی بەڵگەنامە", "حقائب بلاستيكية للوثائق", "documents"),
        WeatherRecommendation("Quick-Dry Towel", "خاولی خێرا وشککەرەوە", "منشفة سريعة الجفاف", "toiletries"),
        WeatherRecommendation("Waterproof Phone Pouch", "بەرگی مۆبایلی دژەئاو", "جراب هاتف ضد الماء", "electronics")
    ),
    WeatherCondition.SNOWY to listOf(
        WeatherRecommendation("Heavy Winter Jacket", "پاڵتۆی ئەستوور", "معطف شتوي ثقيل", "clothing"),
        WeatherRecommendation("Thermal Underwear", "جلی ژێرەوەی گەرم", "ملابس حرارية داخلية", "clothing"),
        WeatherRecommendation("Woolen Beanie & Scarf", "کڵاو و ملپێچی خوری", "قبعة وشاح صوف", "clothing"),
        WeatherRecommendation("Insulated Gloves", "دەستکێشی گەرم", "قفازات دافئة", "clothing"),
        WeatherRecommendation("Lip Balm & Moisturizer", "مەرهەمی لێو و شەکەرە", "مرطب شفاه وكريم ترطيب", "toiletries"),
        WeatherRecommendation("Thermal Beverage Flask", "تەرمۆسی خواردنەوەی گەرم", "حافظة سوائل حرارية", "other"),
        WeatherRecommendation("Hand Warmers", "کیسی گەرمکەرەوەی دەست", "مدفئ يدين محمول", "other")
    ),
    WeatherCondition.WINDY to listOf(
        WeatherRecommendation("Windbreaker Jacket", "چاکەتی دژەبا", "سترة واقية من الرياح", "clothing"),
        WeatherRecommendation("Scarf / Bandana", "ملپێچ یان دەسماڵ", "وشاح أو غطاء رأس", "clothing"),
        WeatherRecommendation("Eye Drops", "قەترەی چاو", "قطرات مرطبة للعين", "toiletries"),
        WeatherRecommendation("Skin Hydration Gel", "جێڵی شێدارکەرەوە", "جل مرطب للبشرة", "toiletries"),
        WeatherRecommendation("Hair Ties / Clips", "بەند و کلیپسی قژ", "ربطات شعر ومشابك", "other")
    )
)

fun getSimulatedForecast(city: String, condition: WeatherCondition, baseTemp: Int): List<Pair<String, Int>> {
    // Generate a beautiful 3-day simulated temperature fluctuation
    val randomOffset = (city.hashCode() % 4).coerceIn(-2, 2)
    val day1Temp = baseTemp + randomOffset
    val day2Temp = baseTemp + randomOffset + 1
    val day3Temp = baseTemp + randomOffset - 1
    return listOf(
        "Today" to day1Temp,
        "Tomorrow" to day2Temp,
        "Day After" to day3Temp
    )
}

data class TouristCountry(
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val flag: String
)

val preloadedCountries = listOf(
    TouristCountry("Iraq", "عێراق", "العراق", "🇮🇶"),
    TouristCountry("United Arab Emirates", "ئیمارات", "الإمارات", "🇦🇪"),
    TouristCountry("Turkey", "تورکیا", "تركيا", "🇹🇷"),
    TouristCountry("Saudi Arabia", "سعوودیە", "السعودية", "🇸🇦"),
    TouristCountry("United Kingdom", "بریتانیا", "المملكة المتحدة", "🇬🇧"),
    TouristCountry("France", "فەڕەنسا", "فرنسا", "🇫🇷"),
    TouristCountry("Italy", "ئیتاڵیا", "إيطاليا", "🇮🇹"),
    TouristCountry("Spain", "ئیسپانيا", "إسبانيا", "🇪🇸"),
    TouristCountry("Egypt", "میسر", "مصر", "🇪🇬"),
    TouristCountry("Jordan", "ئوردن", "الأردن", "🇯🇴"),
    TouristCountry("Lebanon", "لوبنان", "لبنان", "🇱🇧"),
    TouristCountry("Switzerland", "سویسرا", "سويسرا", "🇨🇭"),
    TouristCountry("Sweden", "سوید", "السويد", "🇸🇪"),
    TouristCountry("Germany", "ئەڵمانیا", "ألمانيا", "🇩🇪"),
    TouristCountry("Japan", "ژاپۆن", "اليابان", "🇯🇵"),
    TouristCountry("South Korea", "کۆریای باشوور", "كوريا الجنوبية", "🇰🇷"),
    TouristCountry("Canada", "کەنەدا", "كندا", "🇨🇦"),
    TouristCountry("United States", "ئەمریکا", "الولايات المتحدة", "🇺🇸"),
    TouristCountry("Australia", "ئوسترالیا", "أستراليا", "🇦🇺"),
    TouristCountry("Indonesia", "ئەندۆنیزیا", "إندونيسيا", "🇮🇩")
)

