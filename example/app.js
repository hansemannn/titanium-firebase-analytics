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
	name: 'My Name',
	value: 'My Value'
});

// Set User-ID
FirebaseAnalytics.setUserID('MyUserID');

// Set screen-name  and screen-class
FirebaseAnalytics.setScreenNameAndScreenClass({
	screenName: 'ScreenName',
	screenClass: 'ScreenClass'
});
