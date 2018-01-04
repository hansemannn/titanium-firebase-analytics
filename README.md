# Firebase Analytics - Titanium Module
Use the native Firebase SDK in Axway Titanium. This repository is part of the [Titanium Firebase](https://github.com/hansemannn/titanium-firebase) project.

## Requirements
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
<service android:name="com.google.android.gms.measurement.AppMeasurementService" android:enabled="true" android:exported="false" />	

<service android:name="com.google.android.gms.measurement.AppMeasurementJobService" android:permission="android.permission.BIND_JOB_SERVICE" android:enabled="true" android:exported="false" />

<service android:name="MY_PACKAGE_NAME.gcm.RegistrationIntentService" android:exported="false" />

<receiver android:name="com.google.android.gms.measurement.AppMeasurementReceiver" android:enabled="true">
   <intent-filter>
      <action android:name="com.google.android.gms.measurement.UPLOAD" />
   </intent-filter>
</receiver>  

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
```

2. Copy the `<string name="google_app_id">YOUR_FIREBASE_APPLICATION_ID</string>` key to the `strings.xml` that is located:
 - Alloy: `<project-dir>/app/platform/android/res/values/strings.xml`
 - Classic: `<project-dir>/platform/android/res/values/strings.xml`

## Download
- [x] [Stable release](https://github.com/hansemannn/titanium-firebase-analytics/releases)
- [x] [![gitTio](http://hans-knoechel.de/shields/shield-gittio.svg?v2)](http://gitt.io/component/firebase.analytics)

## API's

### `FirebaseAnalytics`

#### Methods

##### `log(name, parameters)`
  - `name` (String)
  - `parameters` (Dictionary)
  
##### `setUserPropertyString(parameters)`
  - `parameters` (Dictionary)
    - `value` (String)
    - `name` (String)

##### `setScreenNameAndScreenClass(parameters)`
  - `parameters` (Dictionary)
    - `screenName` (String)
    - `screenClass` (String, iOS only)

#### Properties

##### `appInstanceID` (String, get, iOS-only)

##### `userID` (String, set, iOS-only)

##### `enabled` (Boolean, set, iOS-only)

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
FirebaseAnalytics.log('My Event', { /* Optional arguments */ });

// Set user-property string
FirebaseAnalytics.setUserPropertyString({
  name: 'My Name',
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

## Build
```js
cd ios
appc ti build -p ios --build-only
```

## Legal

This module is Copyright (c) 2017-Present by Appcelerator, Inc. All Rights Reserved. 
Usage of this module is subject to the Terms of Service agreement with Appcelerator, Inc.  
