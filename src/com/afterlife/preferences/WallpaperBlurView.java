/*
 * Copyright (C) 2021 Project Radiant
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

package com.afterlife.preferences;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.RenderEffect;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WallpaperBlurView extends ImageView {
	
	public WallpaperBlurView(Context context, AttributeSet attrs) {
		super(context, attrs);
		new SettingsObserver(new Handler()).observe();
		updateBlurView();
	}
	
	private void updateBlurView() {
		int blurEffect = Settings.System.getInt(mContext.getContentResolver(), "declan_wpblur_radius", 15);
		WallpaperManager wm = WallpaperManager.getInstance(mContext);
		if (blurEffect != 0) {
			setRenderEffect(RenderEffect.createBlurEffect(blurEffect, blurEffect, TileMode.CLAMP));
		}
		setImageDrawable(wm.getDrawable());
	}
	
	class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}
		
		public void observe() {
			ContentResolver cr = mContext.getContentResolver();
			cr.registerContentObserver(Settings.System.getUriFor("declan_wpblur_radius"), false, this);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			updateBlurView();
		}
	}
}