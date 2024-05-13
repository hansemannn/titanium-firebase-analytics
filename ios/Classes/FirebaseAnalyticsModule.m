/**
 * titanium-firebase-analytics
 *
 * Created by Hans Knöchel
 * Copyright (c) 2017-present Hans Knöchel. All rights reserved.
 */

#import <FirebaseInstallations/FirebaseInstallations.h>
#import "FirebaseAnalyticsModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"

@import FirebaseCore;
@import FirebaseAnalytics;

@implementation FirebaseAnalyticsModule

#pragma mark Internal

- (id)moduleGUID
{
  return @"9800b2f6-460e-4caa-bf9a-35b206c5d3af";
}

- (NSString *)moduleId
{
  return @"firebase.analytics";
}

#pragma mark Lifecycle

- (void)startup
{
  [super startup];
  NSLog(@"[DEBUG] %@ loaded", self);
}

#pragma Public APIs

- (void)log:(id)arguments
{
  NSString *name = nil;
  NSDictionary *parameters = nil;

  ENSURE_ARG_AT_INDEX(name, arguments, 0, NSString);
  ENSURE_ARG_OR_NIL_AT_INDEX(parameters, arguments, 1, NSDictionary);

  [FIRAnalytics logEventWithName:name
                      parameters:parameters];
}

- (void)setUserPropertyString:(id)arguments
{
  DEPRECATED_REPLACED(@"userPropertyString", @"5.1.0", @"saveUserProperty({ name: '...', value: '...' })");
  [self saveUserProperty:arguments];
}

- (void)saveUserProperty:(id)arguments
{
  ENSURE_SINGLE_ARG(arguments, NSDictionary);

  NSString *value = [arguments objectForKey:@"value"];
  NSString *name = [arguments objectForKey:@"name"];

  [FIRAnalytics setUserPropertyString:value
                              forName:name];
}

- (void)setConsent:(id)arguments
{
    ENSURE_SINGLE_ARG(arguments, NSDictionary);

    BOOL analyticsStorage = [TiUtils boolValue:@"analyticsStorage" properties:arguments def:NO];
    BOOL adStorage = [TiUtils boolValue:@"adStorage" properties:arguments def:NO];
    BOOL adUserData = [TiUtils boolValue:@"adUserData" properties:arguments def:NO];
    BOOL adPersonalization = [TiUtils boolValue:@"adPersonalization" properties:arguments def:NO];

    NSDictionary *consentDictionary = @{
        FIRConsentTypeAnalyticsStorage : analyticsStorage ? FIRConsentStatusGranted : FIRConsentStatusDenied,
        FIRConsentTypeAdStorage : adStorage ? FIRConsentStatusGranted : FIRConsentStatusDenied,
        FIRConsentTypeAdUserData : adUserData ? FIRConsentStatusGranted : FIRConsentStatusDenied,
        FIRConsentTypeAdPersonalization : adPersonalization ? FIRConsentStatusGranted : FIRConsentStatusDenied
    };
    
    [FIRAnalytics setConsent:consentDictionary];
}

- (void)setUserID:(id)userID
{
  if (IS_NULL_OR_NIL(userID)) {
    [FIRAnalytics setUserID:nil];
  } else {
    [FIRAnalytics setUserID:userID];
  }
}

- (void)setEnabled:(NSNumber *)enabled
{
  [FIRAnalytics setAnalyticsCollectionEnabled:[TiUtils boolValue:enabled]];
}

- (void)resetAnalyticsData:(id)unused
{
  [FIRAnalytics resetAnalyticsData];
}

- (void)setScreenNameAndScreenClass:(id)arguments
{
  ENSURE_SINGLE_ARG(arguments, NSDictionary);

  NSString *screenName = [TiUtils stringValue:@"screenName" properties:arguments];
  NSString *screenClass = [TiUtils stringValue:@"screenClass" properties:arguments];

  NSMutableDictionary *parameters = [NSMutableDictionary dictionaryWithDictionary:@{ kFIRParameterScreenName: screenName }];

  if (screenClass != nil) {
    parameters[kFIRParameterScreenClass] = screenClass;
  }

  [FIRAnalytics logEventWithName:kFIREventScreenView parameters:parameters];
}

- (NSString *)fetchInstallationID:(id)callback
{
  ENSURE_SINGLE_ARG(callback, KrollCallback);

  [[FIRInstallations installations] installationIDWithCompletion:^(NSString * _Nullable identifier, NSError * _Nullable error) {
    if (error != nil) {
      [callback call:@[ @{@"success": @(NO), @"error": NULL_IF_NIL(error.localizedDescription) } ] thisObject:self];
      return;
    }

    [callback call:@[ @{ @"success": @(YES), @"identifier": NULL_IF_NIL(identifier) } ] thisObject:self];
  }];
}

- (NSString *)appInstanceID
{
  return [FIRAnalytics appInstanceID];
}

- (void)fetchAppInstanceID:(id)callback
{
  ENSURE_SINGLE_ARG(callback, KrollCallback);
  [callback call:@[@{ @"appInstanceID": [self appInstanceID]}] thisObject:self];
}

@end
