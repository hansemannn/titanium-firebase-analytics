/**
 * titanium-firebase-analytics
 *
 * Created by Hans Knoechel
 * Copyright (c) 2017 Your Company. All rights reserved.
 */

#import "TiModule.h"

@interface FirebaseAnalyticsModule : TiModule {
}

- (void)configure:(id)arguments;

- (void)log:(id)arguments;

- (void)setUserPropertyString:(id)arguments;

- (void)setUserID:(NSString *)userID;

- (void)setEnabled:(NSNumber *)enabled;

- (void)resetAnalyticsData:(id)unused;

- (void)setScreenNameAndScreenClass:(id)arguments;

- (NSString *)appInstanceID;

@end
