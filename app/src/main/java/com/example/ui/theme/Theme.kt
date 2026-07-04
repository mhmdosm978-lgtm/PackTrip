package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = ElectricBlue,
    onPrimary = PureWhite,
    secondary = GoldYellow,
    onSecondary = Black,
    tertiary = LightBlueAccent,
    onTertiary = Black,
    background = Black,
    onBackground = PureWhite,
    surface = DarkGray,
    onSurface = PureWhite,
    surfaceVariant = MediumGray,
    onSurfaceVariant = LightGray,
    outline = GrayBorder
  )

private val LightColorScheme =
  lightColorScheme(
    primary = DeepBlue,
    onPrimary = PureWhite,
    secondary = DarkYellow,
    onSecondary = PureWhite,
    tertiary = ElectricBlue,
    onTertiary = PureWhite,
    background = OffWhite,
    onBackground = Black,
    surface = PureWhite,
    onSurface = Black,
    surfaceVariant = LightGray,
    onSurfaceVariant = Black,
    outline = LightGray
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
