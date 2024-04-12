/*
 * Copyright (C) 2023 The risingOS Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.afterlife.preferences

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.AttributeSet
import android.widget.ImageView

class WallpaperView : ImageView {

    private val handler = Handler()
    private var currentWallpaperDrawable: Drawable? = null

    private val wallpaperChecker = object : Runnable {
        override fun run() {
            setWallpaperPreview()
            handler.postDelayed(this, 2000)
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setWallpaperPreview()
        handler.postDelayed(wallpaperChecker, 2000)
    }

    private fun setWallpaperPreview() {
        val wallpaperManager = WallpaperManager.getInstance(context)
        val wallpaperDrawable: Drawable? = wallpaperManager.drawable

        if (wallpaperDrawable != currentWallpaperDrawable) {
            currentWallpaperDrawable = wallpaperDrawable
            wallpaperDrawable?.let {
                setImageDrawable(it)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(wallpaperChecker)
    }
}
