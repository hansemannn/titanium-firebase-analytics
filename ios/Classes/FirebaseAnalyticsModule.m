/**
 * titanium-firebase-analytics
 *
 * Created by Hans Knöchel
 * Copyright (c) 2017-present Hans Knöchel. All rights reserved.
 */

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
  ENSURE_SINGLE_ARG(arguments, NSDictionary);

  NSString *value = [arguments objectForKey:@"value"];
  NSString *name = [arguments objectForKey:@"name"];

  [FIRAnalytics setUserPropertyString:value
                              forName:name];
}

- (void)setUserID:(NSString *)userID
{
  [FIRAnalytics setUserID:userID];
}

- (void)setEnabled:(NSNumber *)enabled
{
  [[FIRAnalyticsConfiguration sharedInstance] setAnalyticsCollectionEnabled:[TiUtils boolValue:enabled]];
}

- (void)resetAnalyticsData:(id)unused
{
  [FIRAnalytics resetAnalyticsData];
}

- (void)setScreenNameAndScreenClass:(id)arguments
{
  ENSURE_SINGLE_ARG(arguments, NSDictionary);

  NSString *screenName = [TiUtils stringValue:@"screenName" properties:arguments];
  NSString *screenClass = [TiUtils stringValue:@"screenClass" properties:arguments def:@"TiController"];

  [FIRAnalytics setScreenName:screenName
                  screenClass:screenClass];
}

- (NSString *)appInstanceID
{
  [FIRAnalytics appInstanceID];
}

@end
