/*
 * Copyright (C) 2021 TenX-OS
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
 * limitations under the License
 */

package com.realme.realmeparts;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.TwoStatePreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import android.content.Context;
import android.content.SharedPreferences;

import java.lang.Math.*;

import com.realme.realmeparts.preferences.VibratorStrengthPreference;

public class DeviceSettings extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "RealmeParts";

    public static final String KEY_DT2W_SWITCH = "dt2w";
    public static final String KEY_OTG_SWITCH = "otg";
    public static final String KEY_SRGB_SWITCH = "srgb";
    public static final String KEY_HBM_SWITCH = "hbm";
    public static final String KEY_DC_SWITCH = "dc";
    public static final String KEY_GAME_SWITCH = "game";
    public static final String TP_LIMIT_ENABLE = "/proc/touchpanel/oplus_tp_limit_enable";
    public static final String TP_DIRECTION = "/proc/touchpanel/oplus_tp_direction";
    public static final String KEY_VIBSTRENGTH = "vib_strength";

    private static TwoStatePreference mDT2WModeSwitch;
    private static TwoStatePreference mOTGModeSwitch;
    private static TwoStatePreference mHBMModeSwitch;
    private static TwoStatePreference mDCModeSwitch;
    private static TwoStatePreference mSRGBModeSwitch;
    private static TwoStatePreference mGameModeSwitch;
    private VibratorStrengthPreference mVibratorStrength;

    private static Context mContext;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.realme_parts, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        String device = FileUtils.getStringProp("ro.build.product", "unknown");

        mDT2WModeSwitch = (TwoStatePreference) findPreference(KEY_DT2W_SWITCH);
        mDT2WModeSwitch.setEnabled(DT2WModeSwitch.isSupported());
        mDT2WModeSwitch.setChecked(DT2WModeSwitch.isCurrentlyEnabled(this.getContext()));
        mDT2WModeSwitch.setOnPreferenceChangeListener(new DT2WModeSwitch());

        mOTGModeSwitch = (TwoStatePreference) findPreference(KEY_OTG_SWITCH);
        mOTGModeSwitch.setEnabled(OTGModeSwitch.isSupported());
        mOTGModeSwitch.setChecked(OTGModeSwitch.isCurrentlyEnabled(this.getContext()));
        mOTGModeSwitch.setOnPreferenceChangeListener(new OTGModeSwitch());

        mDCModeSwitch = (TwoStatePreference) findPreference(KEY_DC_SWITCH);
        mDCModeSwitch.setEnabled(DCModeSwitch.isSupported());
        mDCModeSwitch.setChecked(DCModeSwitch.isCurrentlyEnabled(this.getContext()));
        mDCModeSwitch.setOnPreferenceChangeListener(new DCModeSwitch());

        mHBMModeSwitch = (TwoStatePreference) findPreference(KEY_HBM_SWITCH);
        mHBMModeSwitch.setEnabled(HBMModeSwitch.isSupported());
        mHBMModeSwitch.setChecked(HBMModeSwitch.isCurrentlyEnabled(this.getContext()));
        mHBMModeSwitch.setOnPreferenceChangeListener(new HBMModeSwitch());

        mSRGBModeSwitch = (TwoStatePreference) findPreference(KEY_SRGB_SWITCH);
        mSRGBModeSwitch.setEnabled(SRGBModeSwitch.isSupported());
        mSRGBModeSwitch.setChecked(SRGBModeSwitch.isCurrentlyEnabled(this.getContext()));
        mSRGBModeSwitch.setOnPreferenceChangeListener(new SRGBModeSwitch());

        mGameModeSwitch = (TwoStatePreference) findPreference(KEY_GAME_SWITCH);
        mGameModeSwitch.setEnabled(GameModeSwitch.isSupported());
        mGameModeSwitch.setChecked(GameModeSwitch.isCurrentlyEnabled(this.getContext()));
        mGameModeSwitch.setOnPreferenceChangeListener(new GameModeSwitch());

        mVibratorStrength = (VibratorStrengthPreference) findPreference(KEY_VIBSTRENGTH);
        if (mVibratorStrength != null) {
            mVibratorStrength.setEnabled(VibratorStrengthPreference.isSupported());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        return true;
    }

    private boolean isAppNotInstalled(String uri) {
        PackageManager packageManager = getContext().getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }
}

