# Firebase Analytics - Titanium Module
Use the native Firebase SDK in Axway Titanium. This repository is part of the [Titanium Firebase](https://github.com/hansemannn/titanium-firebase) project.

## Requirements
- [x] Titanium SDK 6.2.0 or later

## Android Notes

Please make sure to check the Android-specific notes in the [main project](https://github.com/hansemannn/titanium-firebase/blob/master/README.md#️-android-note).

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

##### `setScreenNameAndScreenClass(parameters)` (iOS-only)
  - `parameters` (Dictionary)
    - `screenName` (String)
    - `screenClass` (String)

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
