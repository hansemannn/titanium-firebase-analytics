/**
 * Ti.FirebaseAnalytics
 * Copyright (c) 2018 by Hans Knöchel.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

package firebase.analytics;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;

import org.appcelerator.titanium.io.TiBaseFile;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiBlob;
import org.appcelerator.titanium.util.TiConvert;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.io.IOException;
import java.util.Map;

@Kroll.module(name="TitaniumFirebaseAnalytics", id="firebase.analytics")
public class TitaniumFirebaseAnalyticsModule extends KrollModule
{
  private static final String LCAT = "TitaniumFirebaseAnalyticsModule";
  private static final boolean DBG = TiConfig.LOGD;
  private static FirebaseAnalytics mFirebaseAnalytics;

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
    // TODO: Uncomment once Ti.PlayServices >= 11.6.0 is used
    // this.analyticsInstance().resetAnalyticsData();
    Log.e("FirebaseAnalytics", "The \"resetAnalyticsData()\" method on Android requires Ti.PlayServices >= 11.6.0");
  }

  @Kroll.method @Kroll.setProperty
  public void setEnabled(Boolean enabled)
  {
    this.analyticsInstance().setAnalyticsCollectionEnabled(enabled);
  }

  @Kroll.method @Kroll.setProperty
  public void setUserPropertyString(KrollDict parameters)
  {
    this.analyticsInstance().setUserProperty(TiConvert.toString(parameters, "name"), TiConvert.toString(parameters, "value"));
  }

  @Kroll.method @Kroll.setProperty
  public void setScreenNameAndScreenClass(KrollDict parameters)
  {
    if (!parameters.containsKey("screenName")) {
      Log.e(LCAT, "Unable to set current screen without the missing \"screenName\" key"); 
      return;
    }

    String screenName = parameters.getString("screenName");
    String screenClass = parameters.optString("screenClass", "TiController");
    
    this.analyticsInstance().setCurrentScreen(getActivity(), screenName, screenClass);
  }

  private static Bundle mapToBundle(Map<String, Object> map)
  {
    if (map == null || map.size() == 0) return null;

    Bundle bundle = new Bundle(map.size());

    for (String key : map.keySet()) {
      Object val = map.get(key);
      if (val == null) {
        bundle.putString(key, null);
      } else if (val instanceof TiBlob) {
        bundle.putByteArray(key, ((TiBlob)val).getBytes());
      } else if (val instanceof TiBaseFile) {
        try {
          bundle.putByteArray(key, ((TiBaseFile)val).read().getBytes());
        } catch (IOException e) {
          Log.e("FirebaseAnalytics", "Unable to put '" + key + "' value into bundle: " + e.getLocalizedMessage(), e);
        }
      } else {
        bundle.putString(key, TiConvert.toString(val));
      }
    }

    return bundle;
  }
}
