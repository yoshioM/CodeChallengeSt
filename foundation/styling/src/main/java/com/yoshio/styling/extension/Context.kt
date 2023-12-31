package com.yoshio.styling.extension

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.yoshio.styling.R
import java.io.File

inline fun <reified T : AppCompatActivity> Context.intentTo(): Intent = Intent(this, T::class.java)

inline fun <reified T : AppCompatActivity> Context.clearIntentTo(): Intent = Intent(this, T::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

data class AlertDialogButton(
        val textButton: String = "Ok",
        val action: ((Dialog) -> Unit)? = null,
        val textColor: Int = R.color.colorOnSecondary,
        val backgroundColor: Int = android.R.color.transparent,
        val closeDialogOnClick: Boolean = true)

fun Context.showAlertDialog(title: String? = null,
                            message: String? = null,
                            cancelable: Boolean = true, negativeButton: AlertDialogButton? = null,
                            neutralButton: AlertDialogButton? = null, positiveButton: AlertDialogButton? = null,
                            @StyleRes theme: Int = R.style.ThemeOverlay_MaterialComponents_Dialog_Alert_Round_Corner) {

    val dialog = AlertDialog.Builder(this, theme).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(cancelable)
        setNegativeButton(negativeButton?.textButton, null)
        setNeutralButton(neutralButton?.textButton, null)
        setPositiveButton(positiveButton?.textButton, null)
    }.create()

    dialog.setOnShowListener {

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).apply {
            positiveButton?.run {
                setTextColor(color(textColor))
                setBackgroundColor(color(backgroundColor))
                setOnClickListener {
                    action?.invoke(dialog)
                    if (closeDialogOnClick) dialog.dismiss()
                }
            }
        }

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).apply {
            negativeButton?.run {
                setTextColor(color(textColor))
                setBackgroundColor(color(backgroundColor))
                setOnClickListener {
                    action?.invoke(dialog)
                    if (closeDialogOnClick) dialog.dismiss()
                }
            }
        }

        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).apply {
            neutralButton?.run {
                setTextColor(color(textColor))
                setBackgroundColor(color(backgroundColor))
                setOnClickListener {
                    action?.invoke(dialog)
                    if (closeDialogOnClick) dialog.dismiss()
                }
            }
        }
    }

    dialog.show()
}

fun Context.getUriForImage(authority: String, directory: String): Uri {
    val photoFile = File.createTempFile(IMAGE_PREFIX, IMAGE_SUFFIX, getStorageDir(directory))
    return FileProvider.getUriForFile(this, authority, photoFile)
}

private fun Context.getStorageDir(directory: String) = File(getExternalFilesDir(DIRECTORY_PICTURES), directory).apply {
    if (!exists()) mkdirs()
}

fun Context.drawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

private const val IMAGE_PREFIX = "IMG_"
private const val IMAGE_SUFFIX = ".jpg"
