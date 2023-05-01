/*
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.reprator.base_android.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import java.util.Locale

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()


fun Context.appDrawable(drawableResource: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableResource)
}

fun Context.appDimension(@DimenRes dimen: Int) = resources.getDimensionPixelSize(dimen)

fun Context.appString(@StringRes stringResourceId: Int, vararg stringArray: String) = getString(stringResourceId, stringArray)

fun Context.appStringsArray(@ArrayRes msg: Int) = resources.getStringArray(msg)

fun Context.appDrawableArray(@ArrayRes msg: Int) = resources.obtainTypedArray(msg)

fun Context.getMergeStringResource(
    @StringRes stringResourceId: Int,
    @StringRes vararg stringArray: Int
): String {
    val totalStringArray = arrayOfNulls<String>(stringArray.size)
    for (i in stringArray.indices) {
        totalStringArray[i] = getString(stringArray[i])
    }
    return String.format(Locale.getDefault(), getString(stringResourceId), *totalStringArray)
}

fun Context.getMergeStringResource(
    @StringRes stringResourceId: Int,
    vararg stringArray: String
): String {
    return getString(stringResourceId, *stringArray)
}

fun Context.goToSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }.also { intent ->
        startActivity(intent)
    }
}

fun Context.appColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.appColorStateList(@ColorRes color: Int) = ContextCompat.getColorStateList(this, color)

fun Context.appInteger(@IntegerRes integer: Int) = resources.getInteger(integer)

fun Context.shortToast(msg: String) = Toast.makeText(
    this,
    msg,
    Toast.LENGTH_SHORT
).show()

fun Context.singleButtonDialog(
    message: String,
    title: String = "",
    buttonName: String = "OK",
    buttonCallBack: () -> Unit = {}
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(buttonName) { dialog, _ ->
            buttonCallBack()
            dialog.dismiss()
        }

    val alert = dialogBuilder.create()
    if (title.isNotEmpty())
        alert.setTitle(title)
    alert.show()
}

fun Context.twoButtonDialog(
    message: String,
    title: String = "",
    positiveButtonName: String = "OK",
    negativeButtonName: String = "Cancel",
    positiveButtonCallBack: () -> Unit = {},
    negativeButtonCallBack: () -> Unit = {}
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveButtonName) { dialog, _ ->
            positiveButtonCallBack()
            dialog.dismiss()
        }.setNegativeButton(negativeButtonName) { dialog, _ ->
            negativeButtonCallBack()
            dialog.cancel()
        }

    val alert = dialogBuilder.create()
    if (title.isNotEmpty())
        alert.setTitle(title)
    alert.show()
}
