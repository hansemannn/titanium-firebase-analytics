# Firebase Analytics - Titanium Module
Use the native Firebase SDK in Axway Titanium. This repository is part of the [Titanium Firebase](https://github.com/hansemannn/titanium-firebase) project.

⚠️ This module already comply to the EU General Data Protection Regulation ([GDPR](https://www.eugdpr.org/)) regulation already.
Use the `enabled` property to enable or disable Analytics and `resetAnalyticsData()` to make Cambridge Analytica angry 😙.

## Requirements
- [x] The [Firebase Core](https://github.com/hansemannn/titanium-firebase-core) module
- [x] iOS: Titanium SDK 6.2.0+
- [x] Android: Titanium SDK 7.0.0+

## Installation

In general, make sure to follow the general instructions described in the [main project](https://github.com/hansemannn/titanium-firebase/blob/master/README.md#️-android-note).

### iOS

No additional setup required for Firebase Analytics on iOS.

## Android

There are a few additional requirements for Firebase Analytics on Android:

1. Copy the following code under the `<application>` tag of your tiapp.xml. Please note to replace
all occurrences of `MY_PACKAGE_NAME` with your actual package name (= `<id>` in your tiapp.xml):

```xml
<android xmlns:android="http://schemas.android.com/apk/res/android">
	<manifest>
		<application>
			<service android:name="com.google.android.gms.measurement.AppMeasurementService" android:enabled="true" android:exported="false" />

			<service android:name="com.google.android.gms.measurement.AppMeasurementJobService" android:permission="android.permission.BIND_JOB_SERVICE" android:enabled="true" android:exported="false" />

			<service android:name="MY_PACKAGE_NAME.gcm.RegistrationIntentService" android:exported="false" />

			<receiver android:name="com.google.android.gms.measurement.AppMeasurementReceiver" android:enabled="true">
			   <intent-filter>
				  <action android:name="com.google.android.gms.measurement.UPLOAD" />
			   </intent-filter>
			</receiver>  
	
			<!-- Only add the GCM-related tags if you are using push notifications as well -->
			<service android:name="MY_PACKAGE_NAME.gcm.GcmIntentService" android:exported="false">
			   <intent-filter>
				  <action android:name="com.google.android.c2dm.intent.RECEIVE" />
			   </intent-filter>
			</service>

			<service android:name="MY_PACKAGE_NAME.gcm.GcmIntentService" android:exported="false">
			   <intent-filter>
				  <action android:name="com.google.android.c2dm.intent.SEND" />
			   </intent-filter>
			</service>

			<service android:name="MY_PACKAGE_NAME.gcm.GcmIDListenerService" android:exported="false">
			   <intent-filter>
				  <action android:name="com.google.android.gms.iid.InstanceID" />
			   </intent-filter>
			</service>
		</application>
	</manifest>
</android>   
```

2. Create a file `strings.xml` that is located at:
   - Alloy: `<project-dir>/app/platform/android/res/values/strings.xml`
   - Classic: `<project-dir>/platform/android/res/values/strings.xml`

   with your Firebase Application ID (mobilesdk_app_id from the JSON file) content:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="google_app_id">YOUR_FIREBASE_APPLICATION_ID</string>
</resources>
```

## Download
- [x] [Stable release](https://github.com/hansemannn/titanium-firebase-analytics/releases)
- [x] [![gitTio](http://hans-knoechel.de/shields/shield-gittio.svg?v2)](http://gitt.io/component/firebase.analytics)

## API's

### `FirebaseAnalytics`

#### Methods

##### `log(name, parameters)`
  - `name` (String)
  - `parameters` (Dictionary, optional)

Logs an app event. The event can have up to 25 parameters. Events with the same name must
have the same parameters. Up to 500 event names are supported.

Make sure to check the [Log Events](https://firebase.google.com/docs/analytics/android/events) docs to validate
  that you are using a valid event name (1st parameter) and parameter structure (2nd event).

##### `setUserPropertyString(parameters)`
  - `parameters` (Dictionary)
    - `value` (String)
    - `name` (String)

Sets a user property to a given value. Up to 25 user property names are supported. Once set, user
property values persist throughout the app lifecycle and across sessions.

##### `setScreenNameAndScreenClass(parameters)`
  - `parameters` (Dictionary)
    - `screenName` (String)
    - `screenClass` (String, defaults to `TiController`)

Sets the current screen name, which specifies the current visual context in your app. This helps identify
the areas in your app where users spend their time and how they interact with your app.


##### `resetAnalyticsData()` (iOS-only, on  Android requires /lib version > 11.6.0)

Clears all analytics data for this app from the device and resets the app instance id.

#### Properties

##### `enabled` (Boolean, set)

Sets whether analytics collection is enabled for this app on this device. This setting is persisted across
app sessions. By default it is enabled.

##### `appInstanceID` (String, get, iOS-only)

Instance ID provides a unique identifier for each app instance and a mechanism to authenticate and authorize
actions (for example, sending an FCM message).

##### `userID` (String, set, iOS-only)

The user ID to ascribe to the user of this app on this device, which must be
non-empty and no more than 256 characters long. Setting userID to nil removes the user ID.

## Example
```js
// Require the Firebase Core module (own project!)
var FirebaseCore = require('firebase.core');

// Require the Firebase Analytics module
var FirebaseAnalytics = require('firebase.analytics');

// Configure Firebase
FirebaseCore.configure();

// Get the App Instance ID
Ti.API.info('App Instance ID: ' + FirebaseAnalytics.appInstanceID);

// Log to the Firebase console
FirebaseAnalytics.log('My_Event', { /* Optional arguments */ });

// Set user-property string
FirebaseAnalytics.setUserPropertyString({
  name: 'My_Name',
  value: 'My Value'
});

// Set User-ID
FirebaseAnalytics.userID = 'MyUserID';

// Set screen-name  and screen-class
FirebaseAnalytics.setScreenNameAndScreenClass({
  screenName: 'ScreenName',
  screenClass: 'ScreenClass'
});

// Toogle analytics on/off (default: on / true)
FirebaseAnalytics.enabled = false;
```

## Update SDK's

- [x] Android: Using Gradle command `gradle getDeps`
- [x] iOS: Downloading the [latest framework](https://firebase.google.com/download/ios)

## Build

```js
cd ios
appc run -p [ios|android] --build-only
```

## Legal

This module is Copyright (c) 2017-Present by Hans Knöchel. All Rights Reserved.
