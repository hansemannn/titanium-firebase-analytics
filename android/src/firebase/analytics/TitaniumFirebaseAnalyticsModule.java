/**
 * Ti.FirebaseAnalytics
 * Copyright (c) 2018 by Hans Knöchel.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package firebase.analytics;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.installations.FirebaseInstallations;

import java.io.IOException;
import java.util.Map;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiBlob;
import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.util.TiConvert;

@Kroll.module(name = "TitaniumFirebaseAnalytics", id = "firebase.analytics")
public class TitaniumFirebaseAnalyticsModule extends KrollModule
{
	private static final String LCAT = "TitaniumFirebaseAnalyticsModule";
	private static final boolean DBG = TiConfig.LOGD;
	private static FirebaseAnalytics mFirebaseAnalytics;

	@SuppressLint("MissingPermission") // Android Studio only doesn't know it's included via tiapp.xml, so all good here!
	private FirebaseAnalytics analyticsInstance()
	{
		if (mFirebaseAnalytics == null) {
			mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());
		}

		return mFirebaseAnalytics;
	}

	@Kroll.method
	public void log(String name, @Kroll.argument(optional = true) KrollDict parameters)
	{
		this.analyticsInstance().logEvent(name, this.mapToBundle(parameters));
	}

	@Kroll.method
	public void resetAnalyticsData()
	{
		this.analyticsInstance().resetAnalyticsData();
	}

	@Kroll.method
	@Kroll.setProperty
	public void setEnabled(Boolean enabled)
	{
		this.analyticsInstance().setAnalyticsCollectionEnabled(enabled);
	}

	@Kroll.method
	@Kroll.setProperty
	public void setUserPropertyString(KrollDict parameters)
	{
		Log.w(LCAT, "The \"userPropertyString\" property is deprecated in favor of the \"saveUserProperty({ name: '...', value: '...'})\" method!");
		this.saveUserProperty(parameters);
	}

	@Kroll.method
	public void saveUserProperty(KrollDict parameters)
	{
		this.analyticsInstance().setUserProperty(TiConvert.toString(parameters, "name"),
				TiConvert.toString(parameters, "value"));
	}

	@Kroll.method
	@Kroll.setProperty
	public void setUserID(String id)
	{
		this.analyticsInstance().setUserId(id);
	}

	@Kroll.method
	public void fetchInstallationID(KrollFunction callback) {
		FirebaseInstallations.getInstance().getId().addOnCompleteListener(task -> {
			KrollDict data = new KrollDict();
			if (!task.isSuccessful()) {
				data.put("success", false);
			} else {
				data.put("success", true);
			}

			data.put("identifier", task.getResult());
			callback.callAsync(getKrollObject(), data);
		});
	}

	@Kroll.method
	@Kroll.setProperty
	public void setScreenNameAndScreenClass(KrollDict parameters)
	{
		if (!parameters.containsKey("screenName")) {
			Log.e(LCAT, "Unable to set current screen without the missing \"screenName\" key");
			return;
		}

		final String screenName = parameters.getString("screenName");
		final String screenClass = parameters.optString("screenClass", "TiController");
		final FirebaseAnalytics instance = analyticsInstance();

		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run()
			{
				Bundle bundle = new Bundle(1);
				bundle.putString(screenName, screenClass);
				instance.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
			}
		});
	}

	private Bundle mapToBundle(Map<String, Object> map)
	{
		if (map == null || map.size() == 0)
			return null;

		Bundle bundle = new Bundle(map.size());

		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (val == null) {
				bundle.putString(key, null);
			} else if (val instanceof TiBlob) {
				bundle.putByteArray(key, ((TiBlob) val).getBytes());
			} else if (val instanceof TiBaseFile) {
				try {
					bundle.putByteArray(key, ((TiBaseFile) val).read().getBytes());
				} catch (IOException e) {
					Log.e("FirebaseAnalytics",
						  "Unable to put '" + key + "' value into bundle: " + e.getLocalizedMessage(), e);
				}
			} else if (val instanceof Double) {
				bundle.putDouble(key, TiConvert.toDouble(val));
			} else if (val instanceof Float) {
				bundle.putDouble(key, TiConvert.toFloat(val));
			} else if (val instanceof Integer) {
				bundle.putDouble(key, TiConvert.toInt(val));
			} else {
				bundle.putString(key, TiConvert.toString(val));
			}
		}

		return bundle;
	}
}
