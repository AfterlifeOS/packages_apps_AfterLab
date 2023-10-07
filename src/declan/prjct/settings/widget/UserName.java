/*
 * Copyright (C) 2023 AfterLife Project
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

package declan.prjct.settings.widget;

import android.content.*;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.util.*;
import android.provider.*;
import android.widget.*;

public class UserName extends RelativeLayout {
	
	private TextView greetingText, nameText;
	
	public UserName(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		greetingText = findViewById(android.R.id.title);
		nameText = findViewById(android.R.id.summary);
		new SettingsObserver(new Handler()).observe();
		updateSettings();
	}
	
	private void updateSettings() {
		String text = Settings.System.getStringForUser(mContext.getContentResolver(), "afterlab_set_username", UserHandle.USER_CURRENT);
		nameText.setText(text == null || text == "" ? "Hi User?" : text);
		if (getResources().getConfiguration().locale.getLanguage().equals("in")) {
			greetingText.setText("Selamat Datang.");
		} else {
			greetingText.setText("Welcome back.");
		}
	}
	
	class SettingsObserver extends ContentObserver {
		SettingsObserver(Handler handler) {
			super(handler);
		}
		
		public void observe() {
			ContentResolver cr = mContext.getContentResolver();
			cr.registerContentObserver(Settings.System.getUriFor("afterlab_set_username"), false, this);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			updateSettings();
		}
	}
}