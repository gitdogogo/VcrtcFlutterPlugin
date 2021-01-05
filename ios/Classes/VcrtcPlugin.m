#import "VcrtcPlugin.h"
#if __has_include(<vcrtc_plugin/vcrtc_plugin-Swift.h>)
#import <vcrtc_plugin/vcrtc_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "vcrtc_plugin-Swift.h"
#endif

@implementation VcrtcPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftVcrtcPlugin registerWithRegistrar:registrar];
}
@end
