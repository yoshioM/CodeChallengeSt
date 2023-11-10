package com.yoshio.styling.extension

import android.Manifest.permission.CAMERA
import androidx.activity.result.ActivityResultLauncher

fun ActivityResultLauncher<String>.requestCameraPermission() = launch(CAMERA)
