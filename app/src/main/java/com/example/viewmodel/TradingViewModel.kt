package com.example.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MarketDataResult
import com.example.data.repository.MarketRepository
import com.example.model.DispatchChannel
import com.example.model.FilterSettings
import com.example.model.MarketDataSource
import com.example.model.NotificationLog
import com.example.model.RadarPair
import com.example.model.RadarSignalType
import com.example.model.SignalDirection
import com.example.model.TradingSignal
import com.example.util.SoundUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TradingViewModel(
  private val repository: MarketRepository = MarketRepository()
) : ViewModel() {

  private val _settings = MutableStateFlow(FilterSettings())
  val settings: StateFlow<FilterSettings> = _settings.asStateFlow()

  private val _isTestingAudio = MutableStateFlow(false)
  val isTestingAudio: StateFlow<Boolean> = _isTestingAudio.asStateFlow()

  private val _isSaving = MutableStateFlow(false)
  val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

  private val _saveMessage = MutableStateFlow<String?>(null)
  val saveMessage: StateFlow<String?> = _saveMessage.asStateFlow()

  // 0: Signals, 1: Confirmations, 2: Radar, 3: Filter & Alerts (Active tab in screenshot)
  private val _currentTab = MutableStateFlow(3)
  val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

  private val _showProfileDialog = MutableStateFlow(false)
  val showProfileDialog: StateFlow<Boolean> = _showProfileDialog.asStateFlow()

  private val _showNotificationsSheet = MutableStateFlow(false)
  val showNotificationsSheet: StateFlow<Boolean> = _showNotificationsSheet.asStateFlow()

  // Real Market Data States
  private val _isLoadingRealData = MutableStateFlow(false)
  val isLoadingRealData: StateFlow<Boolean> = _isLoadingRealData.asStateFlow()

  private val _isRealDataConnected = MutableStateFlow(true)
  val isRealDataConnected: StateFlow<Boolean> = _isRealDataConnected.asStateFlow()

  private val _lastUpdateFormatted = MutableStateFlow("همین الان")
  val lastUpdateFormatted: StateFlow<String> = _lastUpdateFormatted.asStateFlow()

  private val _radarPairs = MutableStateFlow<List<RadarPair>>(emptyList())
  val radarPairs: StateFlow<List<RadarPair>> = _radarPairs.asStateFlow()

  private val _signals = MutableStateFlow<List<TradingSignal>>(emptyList())
  val signals: StateFlow<List<TradingSignal>> = _signals.asStateFlow()

  private val _notifications = MutableStateFlow<List<NotificationLog>>(emptyList())
  val notifications: StateFlow<List<NotificationLog>> = _notifications.asStateFlow()

  private var pollingJob: Job? = null

  init {
    startRealMarketStream()
  }

  fun startRealMarketStream() {
    pollingJob?.cancel()
    pollingJob = viewModelScope.launch {
      while (isActive) {
        refreshRealMarketData()
        delay(6000) // Poll real market every 6 seconds
      }
    }
  }

  fun refreshRealMarketData() {
    viewModelScope.launch {
      _isLoadingRealData.value = true
      when (val result = repository.fetchRealMarketData(_settings.value)) {
        is MarketDataResult.Success -> {
          _radarPairs.value = result.pairs
          _signals.value = result.signals

          val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
          _lastUpdateFormatted.value = sdf.format(Date(result.timestamp))
          _isRealDataConnected.value = true

          // Check if any high confluence signal should trigger a notification
          if (result.signals.isNotEmpty() && _notifications.value.isEmpty()) {
            val topSig = result.signals.first()
            val newNotif = NotificationLog(
              id = "LIVE-${System.currentTimeMillis()}",
              title = if (topSig.type == SignalDirection.LONG) "شکار کف قطعی (Long Alert) - ${topSig.symbol}" else "هشدار سقف بحرانی (Short Alert) - ${topSig.symbol}",
              description = "تاییدیه شاخص‌های همپوشان هوشمند با همگرایی در نرخ $%,.2f".format(Locale.US, topSig.entryPrice),
              timestamp = "لحظه‌ای (زنده)",
              direction = topSig.type,
              isUnread = true
            )
            _notifications.value = listOf(newNotif)
          }
        }
        is MarketDataResult.Error -> {
          // If error occurs, keep last data and mark status
          _isRealDataConnected.value = false
        }
      }
      _isLoadingRealData.value = false
    }
  }

  fun setMarketDataSource(source: MarketDataSource) {
    _settings.value = _settings.value.copy(preferredDataSource = source)
    refreshRealMarketData()
  }

  fun setThreshold(threshold: Int) {
    _settings.value = _settings.value.copy(threshold = threshold)
    refreshRealMarketData()
  }

  fun toggleDivergence() {
    _settings.value = _settings.value.copy(divergence = !_settings.value.divergence)
    refreshRealMarketData()
  }

  fun toggleCvdVolume() {
    _settings.value = _settings.value.copy(cvdVolume = !_settings.value.cvdVolume)
    refreshRealMarketData()
  }

  fun toggleLiquiditySweeps() {
    _settings.value = _settings.value.copy(liquiditySweeps = !_settings.value.liquiditySweeps)
    refreshRealMarketData()
  }

  fun toggleReversalCandles() {
    _settings.value = _settings.value.copy(reversalCandles = !_settings.value.reversalCandles)
    refreshRealMarketData()
  }

  fun toggleWhaleDepth() {
    _settings.value = _settings.value.copy(whaleDepth = !_settings.value.whaleDepth)
    refreshRealMarketData()
  }

  fun toggleLongAlert() {
    _settings.value = _settings.value.copy(longAlert = !_settings.value.longAlert)
    refreshRealMarketData()
  }

  fun toggleShortAlert() {
    _settings.value = _settings.value.copy(shortAlert = !_settings.value.shortAlert)
    refreshRealMarketData()
  }

  fun setDispatchChannel(channel: DispatchChannel) {
    _settings.value = _settings.value.copy(dispatchChannel = channel)
  }

  fun selectTab(tab: Int) {
    _currentTab.value = tab
  }

  fun setProfileDialogVisible(visible: Boolean) {
    _showProfileDialog.value = visible
  }

  fun setNotificationsSheetVisible(visible: Boolean) {
    _showNotificationsSheet.value = visible
  }

  fun triggerAudioTest(context: Context) {
    if (_isTestingAudio.value) return
    viewModelScope.launch {
      _isTestingAudio.value = true
      SoundUtil.playCyberAlert(context)
      delay(1300)
      _isTestingAudio.value = false
    }
  }

  fun saveSettings() {
    viewModelScope.launch {
      _isSaving.value = true
      delay(500)
      _isSaving.value = false
      _saveMessage.value = "تنظیمات و فیلترهای اعتبارسنجی با موفقیت روی بازار زنده اعمال شد"
      delay(2500)
      _saveMessage.value = null
    }
  }

  fun markAllNotificationsRead() {
    _notifications.value = _notifications.value.map { it.copy(isUnread = false) }
  }

  override fun onCleared() {
    super.onCleared()
    pollingJob?.cancel()
  }
}
