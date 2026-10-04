package com.example

import com.example.model.DispatchChannel
import com.example.viewmodel.TradingViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun tradingViewModel_defaultSettings_strictThresholdAndFiveIndicators() {
    val viewModel = TradingViewModel()
    val settings = viewModel.settings.value

    assertEquals(4, settings.threshold)
    assertEquals(5, settings.activeIndicatorsCount)
    assertTrue(settings.divergence)
    assertTrue(settings.cvdVolume)
    assertTrue(settings.liquiditySweeps)
    assertTrue(settings.reversalCandles)
    assertTrue(settings.whaleDepth)
    assertTrue(settings.longAlert)
    assertTrue(settings.shortAlert)
    assertEquals(DispatchChannel.PHONE_DIRECT, settings.dispatchChannel)
  }

  @Test
  fun tradingViewModel_toggleThresholdAndIndicators() {
    val viewModel = TradingViewModel()
    viewModel.setThreshold(3)
    assertEquals(3, viewModel.settings.value.threshold)

    viewModel.toggleDivergence()
    assertFalse(viewModel.settings.value.divergence)
    assertEquals(4, viewModel.settings.value.activeIndicatorsCount)

    viewModel.setDispatchChannel(DispatchChannel.TELEGRAM_VIP)
    assertEquals(DispatchChannel.TELEGRAM_VIP, viewModel.settings.value.dispatchChannel)
  }
}
