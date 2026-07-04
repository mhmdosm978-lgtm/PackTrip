package com.example.ui

data class TemplateItem(
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val category: String
)

data class PackingTemplate(
    val id: String,
    val nameEn: String,
    val nameKu: String,
    val nameAr: String,
    val icon: String,
    val items: List<TemplateItem>
)

val packingTemplates = listOf(
    PackingTemplate(
        id = "beach",
        nameEn = "Beach Trip",
        nameKu = "گەشتی دەریا",
        nameAr = "رحلة بحرية",
        icon = "🏖️",
        items = listOf(
            TemplateItem("Swimwear", "جلی مەلەکردن", "ملابس السباحة", "clothing"),
            TemplateItem("Beach Towel", "خاولی دەریا", "منشفة البحر", "toiletries"),
            TemplateItem("Sunscreen Cream", "دژە خۆر", "كريم واقي الشمس", "toiletries"),
            TemplateItem("Flip Flops", "نەعل یان شەحتە", "نعال بحر", "clothing"),
            TemplateItem("Waterproof Bag", "جانتا دژە ئاو", "حقيبة مقاومة للماء", "other"),
            TemplateItem("Sunglasses", "چاویلکەی خۆر", "نظارات شمسية", "clothing")
        )
    ),
    PackingTemplate(
        id = "business",
        nameEn = "Business Trip",
        nameKu = "گەشتی کار",
        nameAr = "رحلة عمل",
        icon = "💼",
        items = listOf(
            TemplateItem("Formal Suit/Outfit", "جلی فەرمی", "بدلة رسمية", "clothing"),
            TemplateItem("Notebook & Pen", "دەفتەر و پێنووس", "دفتر ملاحظات وقلم", "documents"),
            TemplateItem("Business Cards", "کارتی کار", "بطاقات العمل", "documents"),
            TemplateItem("Laptop & Charger", "لاپتۆپ و بارگاوی کەرەوە", "كمبيوتر محمول وشاحن", "electronics"),
            TemplateItem("Breath Mints", "حەبی بۆنخۆشکردنی دەم", "حبوب منعشة للفم", "toiletries"),
            TemplateItem("Formal Shoes", "پێڵاوی فەرمی", "حذاء رسمي", "clothing")
        )
    ),
    PackingTemplate(
        id = "hiking",
        nameEn = "Hiking & Outdoor",
        nameKu = "پیاسەکردنی شاخ",
        nameAr = "رحلة هايكنج",
        icon = "🥾",
        items = listOf(
            TemplateItem("Hiking Boots", "پێڵاوی شاخ", "حذاء هايكنج", "clothing"),
            TemplateItem("Water Bottle", "مەترەقەی ئاو", "زجاجة ماء", "other"),
            TemplateItem("Backpack", "جانتای پشت", "حقيبة ظهر مناسبة", "other"),
            TemplateItem("First-Aid Kit", "پێداویستی فریاگوزاری", "حقيبة إسعافات أولية", "toiletries"),
            TemplateItem("Insect Repellent", "دژە مێشوولە", "بخاخ طارد الحشرات", "toiletries"),
            TemplateItem("Power Bank", "پاوەربانک", "شاحن سفري / باوربانك", "electronics")
        )
    ),
    PackingTemplate(
        id = "family",
        nameEn = "Family Vacation",
        nameKu = "گەشتی خێزانی",
        nameAr = "إجازة عائلية",
        icon = "👨‍👩‍👧‍👦",
        items = listOf(
            TemplateItem("Wet Wipes", "سڕکەری تەڕ", "مناديل مبللة", "toiletries"),
            TemplateItem("Kids Extra Clothes", "جلی زیادەی منداڵ", "ملابس إضافية للأطفال", "clothing"),
            TemplateItem("Travel Pillows", "سەرینی گەشت", "وسائد سفر", "other"),
            TemplateItem("Snacks & Treats", "سووکە ژەم بۆ ڕێگا", "وجبات خفيفة وسناكس", "other"),
            TemplateItem("Family Medicines", "دەرمانی گشتی خێزان", "أدوية عامة للعائلة", "toiletries"),
            TemplateItem("Kids Entertainment/Toys", "یاری منداڵان", "ألعاب ترفيه للأطفال", "other")
        )
    )
)
