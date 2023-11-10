package com.yoshio.styling.extension

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hasCameraPermission() =
        ActivityCompat.checkSelfPermission(this, CAMERA) == PERMISSION_GRANTED
