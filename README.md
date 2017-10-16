# Firebase Analytics - Titanium Module
Use the native Firebase SDK in Axway Titanium. This repository is part of the [Titanium Firebase](https://github.com/hansemannn/titanium-firebase) project.

## Requirements
- [x] Titanium SDK 6.2.0 or later

## API's

### `FirebaseAnalytics`

#### Methods

##### `configure()`

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
    - `screenClass` (String)

#### Properties

##### `appInstanceID` (String, get)

##### `userID` (String, set)

##### `enabled` (Boolean, set)

## Example
```js
// Require the Firebase Analytics module
var FirebaseAnalytics = require('firebase.analytics');

// Configure FirebaseAnalytics
FirebaseAnalytics.configure();

// Get the App Instance ID
Ti.API.info('App Instance ID: ' + FirebaseAnalytics.appInstanceID);

// Log to the Firebase console
FirebaseAnalytics.log('My Event', { /* Optional arguments */ });

// Set user-property string
FirebaseAnalytics.setUserPropertyString({
  name: 'My Name'
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
cd iphone
appc ti build -p ios --build-only
```

## Legal

This module is Copyright (c) 2017-Present by Appcelerator, Inc. All Rights Reserved. 
Usage of this module is subject to the Terms of Service agreement with Appcelerator, Inc.  
