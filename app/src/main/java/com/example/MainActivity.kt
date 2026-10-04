package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopHeader
import com.example.ui.dialogs.NotificationsSheet
import com.example.ui.dialogs.ProfileDialog
import com.example.ui.screens.AlertSettingsScreen
import com.example.ui.screens.ConfirmationsScreen
import com.example.ui.screens.FloorCeilingRadarScreen
import com.example.ui.screens.SignalsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.VoidDark
import com.example.viewmodel.TradingViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: TradingViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        // Enforce RTL for proper Persian / Arabic layout mirroring identical to the reference screenshot
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
          TradingApp(viewModel = viewModel)
        }
      }
    }
  }
}

@Composable
fun TradingApp(viewModel: TradingViewModel) {
  val currentTab by viewModel.currentTab.collectAsState()
  val showProfileDialog by viewModel.showProfileDialog.collectAsState()
  val showNotificationsSheet by viewModel.showNotificationsSheet.collectAsState()
  val notifications by viewModel.notifications.collectAsState()
  val saveMessage by viewModel.saveMessage.collectAsState()
  val isLoadingRealData by viewModel.isLoadingRealData.collectAsState()
  val isRealDataConnected by viewModel.isRealDataConnected.collectAsState()
  val lastUpdateFormatted by viewModel.lastUpdateFormatted.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(saveMessage) {
    if (saveMessage != null) {
      snackbarHostState.showSnackbar(saveMessage ?: "")
    }
  }

  // Handle hardware Back button to return to Filter & Alert tab if in secondary tab
  BackHandler(enabled = currentTab != 3) {
    viewModel.selectTab(3)
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(VoidDark)
      .statusBarsPadding(),
    topBar = {
      TopHeader(
        unreadNotificationsCount = notifications.count { it.isUnread },
        onNotificationsClick = { viewModel.setNotificationsSheetVisible(true) },
        onProfileClick = { viewModel.setProfileDialogVisible(true) },
        onRefreshClick = { viewModel.refreshRealMarketData() },
        isRefreshing = isLoadingRealData,
        isLiveConnected = isRealDataConnected,
        lastUpdateTime = lastUpdateFormatted,
        subtitleText = when (currentTab) {
          0 -> "Signals Feed"
          1 -> "Confirmations"
          2 -> "Floor & Ceiling"
          else -> "Alert Settings"
        }
      )
    },
    bottomBar = {
      BottomNavBar(
        selectedTab = currentTab,
        onTabSelected = { viewModel.selectTab(it) }
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    containerColor = VoidDark
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Crossfade(
        targetState = currentTab,
        label = "tab_transition"
      ) { tabIndex ->
        when (tabIndex) {
          0 -> SignalsScreen(viewModel = viewModel)
          1 -> ConfirmationsScreen(viewModel = viewModel)
          2 -> FloorCeilingRadarScreen(viewModel = viewModel)
          3 -> AlertSettingsScreen(viewModel = viewModel)
          else -> AlertSettingsScreen(viewModel = viewModel)
        }
      }
    }

    if (showProfileDialog) {
      ProfileDialog(
        onDismiss = { viewModel.setProfileDialogVisible(false) }
      )
    }

    if (showNotificationsSheet) {
      NotificationsSheet(
        viewModel = viewModel,
        onDismiss = { viewModel.setNotificationsSheetVisible(false) }
      )
    }
  }
}
