/*
 * Copyright 2021 Vikram LLC
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

package app.reprator.base_android.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Scale
import app.reprator.base_android.R
import app.reprator.base_android.extensions.drawableFromViewContext

@BindingAdapter(
    value = [
        "imageUrl",
        "placeHolder",
        "errorDrawable",
        "dimension"
    ],
    requireAll = false
)
fun ImageView.imageLoad(
    imageUrl: String?,
    placeHolder: Drawable?= null,
    @DrawableRes errorDrawable: Int?= null,
    dimension: String?= null
) {
    val errorDrawableValid = drawableFromViewContext(errorDrawable ?: R.drawable.ic_error)

    if (imageUrl.isNullOrBlank()) {
        val drawable = errorDrawableValid ?: placeHolder
        load(drawable)
    } else {

        val url = if (dimension.isNullOrEmpty())
            imageUrl
        else
            "$imageUrl?$dimension"

        load(url) {
            val placeHolderDrawable =
                placeHolder ?: drawableFromViewContext(R.drawable.ic_circles_loader)
            placeholder(placeHolderDrawable)

            error(errorDrawableValid)
            scale(Scale.FILL)
        }
    }
}