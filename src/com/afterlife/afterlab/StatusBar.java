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

import android.content.Context;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import android.text.format.DateFormat;
import com.afterlife.support.preference.SecureSettingListPreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.Utils;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.android.settingslib.development.SystemPropPoker;
import android.os.SystemProperties;
import com.afterlife.support.preference.SystemSettingSwitchPreference;
import com.afterlife.support.preference.SystemSettingMainSwitchPreference;
import android.os.Process;

import com.android.settingslib.development.SystemPropPoker;
import android.os.SystemProperties;

@SearchIndexable
public class StatusBar extends SettingsPreferenceFragment 
            implements Preference.OnPreferenceChangeListener {


    private static final String KEY_STATUS_BAR_AM_PM = "status_bar_am_pm";
    private SecureSettingListPreference mStatusBarAmPm;
    private Preference mCombinedSignalIcons;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.category_statusbar);
        final ContentResolver resolver = getActivity().getContentResolver();
        mStatusBarAmPm = findPreference(KEY_STATUS_BAR_AM_PM);
        PreferenceScreen prefSet = getPreferenceScreen();
        final Resources res = getResources();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        
        mCombinedSignalIcons = findPreference("persist.sys.flags.combined_signal_icons");
        mCombinedSignalIcons.setOnPreferenceChangeListener(this);
    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mCombinedSignalIcons) {
            boolean value = (Boolean) objValue;
            Settings.Secure.putIntForUser(getContentResolver(),
                Settings.Secure.ENABLE_COMBINED_SIGNAL_ICONS, value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        } 
        return false;
    }
    
    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.AFTERLIFE;
    }

    @Override
    public void onResume() {
        super.onResume();
	if (DateFormat.is24HourFormat(requireContext())) {
            mStatusBarAmPm.setEnabled(false);
            mStatusBarAmPm.setSummary(R.string.status_bar_am_pm_unavailable);
            }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.category_statusbar;
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
            };
}