package com.example.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object SoundUtil {
  suspend fun playCyberAlert(context: Context) {
    withContext(Dispatchers.IO) {
      try {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
          vibratorManager?.defaultVibrator
        } else {
          @Suppress("DEPRECATION")
          context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          vibrator?.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
          @Suppress("DEPRECATION")
          vibrator?.vibrate(150)
        }

        val toneGen = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 90)
        toneGen.startTone(ToneGenerator.TONE_PROP_PROMPT, 120)
        delay(160)
        toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 250)
        delay(260)
        toneGen.release()
      } catch (e: Exception) {
        // Fallback safely if audio service has restrictions
      }
    }
  }
}
