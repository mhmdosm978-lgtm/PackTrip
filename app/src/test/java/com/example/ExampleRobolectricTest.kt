package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.WeatherCondition
import com.example.ui.getSimulatedForecast
import com.example.ui.preloadedCities
import com.example.ui.weatherRecommendations
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("PickTrip", appName)
  }

  @Test
  fun `verify preloaded cities contain erbil and london`() {
    val cityNames = preloadedCities.map { it.nameEn }
    assertTrue(cityNames.contains("Erbil"))
    assertTrue(cityNames.contains("London"))
  }

  @Test
  fun `verify weather recommendations map has suggestions for sunny condition`() {
    val sunnyRecs = weatherRecommendations[WeatherCondition.SUNNY]
    assertNotNull(sunnyRecs)
    assertTrue(sunnyRecs!!.isNotEmpty())
    
    val hasClothing = sunnyRecs.any { it.category == "clothing" }
    assertTrue(hasClothing)
  }

  @Test
  fun `verify simulated forecast returns correct three day list`() {
    val forecast = getSimulatedForecast("Erbil", WeatherCondition.SUNNY, 38)
    assertEquals(3, forecast.size)
    assertEquals("Today", forecast[0].first)
    assertEquals("Tomorrow", forecast[1].first)
    assertEquals("Day After", forecast[2].first)
  }
}
