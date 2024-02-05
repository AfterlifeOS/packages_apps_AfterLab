/*
 * Copyright (C) 2020 Project-Awaken
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
package com.afterlife.afterlab;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;

import com.afterlife.support.preference.SecureSettingSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SearchIndexable
public class LockScreen extends SettingsPreferenceFragment 
            implements Preference.OnPreferenceChangeListener {

    private static final String FINGERPRINT_SUCCESS_VIB = "fingerprint_success_vib";
    private static final String FINGERPRINT_ERROR_VIB = "fingerprint_error_vib";
    private static final String LOCKSCREEN_DOUBLE_LINE_CLOCK = "lockscreen_double_line_clock_switch";
    private static final String AOD_SCHEDULE_KEY = "always_on_display_schedule";
    private static final String CLOCK_LS = "clock_ls";
    private static final String CLOCK_PREVIEW= "lockscreen_preview";
    
    private FingerprintManager mFingerprintManager;
    private SwitchPreference mFingerprintSuccessVib;
    private SwitchPreference mFingerprintErrorVib;
    private SecureSettingSwitchPreference mDoubleLineClock, mCustomClock;
    private Preference mAODPref, mPreviewClock;
    
	public static final int MODE_DISABLED = 0;
    public static final int MODE_NIGHT = 1;
    public static final int MODE_TIME = 2;
    public static final int MODE_MIXED_SUNSET = 3;
    public static final int MODE_MIXED_SUNRISE = 4;

    private static final String KEY_FOOTER_AVATAR_URI = "declan_avatar_picker_uri";
	private static final int REQUEST_AVATAR_PICKER = 10001;

	private Preference mAvatarPicker;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.category_lockscreen);
        
        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefSet = getPreferenceScreen();
        final PackageManager mPm = getActivity().getPackageManager();

        mAvatarPicker = findPreference(KEY_FOOTER_AVATAR_URI);

        mDoubleLineClock = (SecureSettingSwitchPreference ) findPreference(LOCKSCREEN_DOUBLE_LINE_CLOCK);
        mDoubleLineClock.setChecked((Settings.Secure.getInt(getContentResolver(),
             Settings.Secure.LOCKSCREEN_USE_DOUBLE_LINE_CLOCK, 1) != 0));
        mDoubleLineClock.setOnPreferenceChangeListener(this);
        
        mCustomClock = (SecureSettingSwitchPreference ) findPreference(CLOCK_LS);
        mCustomClock.setChecked((Settings.Secure.getInt(getContentResolver(),
             Settings.Secure.CLOCK_LS, 1) != 0));
        mCustomClock.setOnPreferenceChangeListener(this);
        if (!mCustomClock.isChecked()) {
        	mDoubleLineClock.setEnabled(true);
        } else {
        	mDoubleLineClock.setEnabled(false);
        }
        mPreviewClock = (Preference ) findPreference(CLOCK_PREVIEW);
        if (!mCustomClock.isChecked()) {
            mPreviewClock.setEnabled(false);
            getPreferenceScreen().removePreference(mPreviewClock);
        } else {
        	mPreviewClock.setEnabled(true);
            getPreferenceScreen().addPreference(mPreviewClock);
        }
        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        mFingerprintSuccessVib = (SwitchPreference) findPreference(FINGERPRINT_SUCCESS_VIB);
        mFingerprintErrorVib = (SwitchPreference) findPreference(FINGERPRINT_ERROR_VIB);
        if (mPm.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) &&
                 mFingerprintManager != null) {
            if (!mFingerprintManager.isHardwareDetected()){
                prefSet.removePreference(mFingerprintSuccessVib);
                prefSet.removePreference(mFingerprintErrorVib);
            } else {
                mFingerprintSuccessVib.setChecked((Settings.System.getInt(getContentResolver(),
                        Settings.System.FP_SUCCESS_VIBRATE, 1) == 1));
                mFingerprintSuccessVib.setOnPreferenceChangeListener(this);
                mFingerprintErrorVib.setChecked((Settings.System.getInt(getContentResolver(),
                        Settings.System.FP_ERROR_VIBRATE, 1) == 1));
                mFingerprintErrorVib.setOnPreferenceChangeListener(this);
            }
        } else {
            prefSet.removePreference(mFingerprintSuccessVib);
            prefSet.removePreference(mFingerprintErrorVib);
        }
        
        mAODPref = findPreference(AOD_SCHEDULE_KEY);
        updateAlwaysOnSummary();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAlwaysOnSummary();
    }

    private void updateAlwaysOnSummary() {
        if (mAODPref == null) return;
        int mode = Settings.Secure.getIntForUser(getActivity().getContentResolver(),
                Settings.Secure.DOZE_ALWAYS_ON_AUTO_MODE, MODE_DISABLED, UserHandle.USER_CURRENT);
        switch (mode) {
            default:
            case MODE_DISABLED:
                mAODPref.setSummary(R.string.disabled);
                break;
            case MODE_NIGHT:
                mAODPref.setSummary(R.string.night_display_auto_mode_twilight);
                break;
            case MODE_TIME:
                mAODPref.setSummary(R.string.night_display_auto_mode_custom);
                break;
            case MODE_MIXED_SUNSET:
                mAODPref.setSummary(R.string.always_on_display_schedule_mixed_sunset);
                break;
            case MODE_MIXED_SUNRISE:
                mAODPref.setSummary(R.string.always_on_display_schedule_mixed_sunrise);
                break;
        }

        final Resources res = getResources();
        final PreferenceScreen prefScreen = getPreferenceScreen();
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mFingerprintSuccessVib) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.FP_SUCCESS_VIBRATE, value ? 1 : 0);
            return true;
        } else if (preference == mFingerprintErrorVib) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.FP_ERROR_VIBRATE, value ? 1 : 0);
            return true;
        } else if (preference == mDoubleLineClock) {
          boolean value = (Boolean) newValue;
            Settings.Secure.putInt(getActivity().getContentResolver(),
                    Settings.Secure.LOCKSCREEN_USE_DOUBLE_LINE_CLOCK, value ? 1 : 0);
            return true;
        } else if (preference == mCustomClock) {
          boolean value = (Boolean) newValue;
            Settings.Secure.putInt(getActivity().getContentResolver(),
                    Settings.Secure.CLOCK_LS, value ? 1 : 0);
            if (mDoubleLineClock.isChecked()) {
            	Settings.Secure.putInt(getActivity().getContentResolver(),
                        Settings.Secure.LOCKSCREEN_USE_DOUBLE_LINE_CLOCK, 0);
                mDoubleLineClock.setChecked(false);
            }
        	if (!mCustomClock.isChecked()) {
            	mDoubleLineClock.setEnabled(false);
                mPreviewClock.setEnabled(true);
                getPreferenceScreen().addPreference(mPreviewClock);
            } else {
            	mDoubleLineClock.setEnabled(true);
                mPreviewClock.setEnabled(false);
                getPreferenceScreen().removePreference(mPreviewClock);
            }
            return true;
        }
        return false;
    }  

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AFTERLIFE;
    }

    @Override
	public boolean onPreferenceTreeClick(Preference preference) {
		if (preference == mAvatarPicker) {
			Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(intent, REQUEST_AVATAR_PICKER);
			return true;
		}
		return super.onPreferenceTreeClick(preference);
    }

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent result) {
		if (requestCode == REQUEST_AVATAR_PICKER) {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			ContentResolver resolver = getContext().getContentResolver();
			final Uri imgUri = result.getData();
			if (imgUri != null) {
				Settings.System.putStringForUser(resolver, "declan_avatar_picker", imgUri.toString(), UserHandle.USER_CURRENT);
			}
		}
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.category_lockscreen;
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
            };
}
