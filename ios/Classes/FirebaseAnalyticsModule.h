/**
 * titanium-firebase-analytics
 *
 * Created by Hans Knöchel
 * Copyright (c) 2017-present Hans Knöchel. All rights reserved.
 */

#import "TiModule.h"

@interface FirebaseAnalyticsModule : TiModule

- (void)log:(id)arguments;

- (void)saveUserProperty:(id)arguments;

- (void)setUserPropertyString:(id)arguments; // DEPRECATED: Use "saveUserProperty(arguments)" method instead

- (void)setConsent:(id)arguments;

- (void)setUserID:(NSString *)userID;

- (void)setEnabled:(NSNumber *)enabled;

- (void)resetAnalyticsData:(id)unused;

- (void)setScreenNameAndScreenClass:(id)arguments;

- (NSString *)fetchInstallationID:(id)callback;

- (NSString *)appInstanceID;

@end
