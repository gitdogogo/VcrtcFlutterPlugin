import 'dart:async';
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class VcrtcPlugin {
  static const MethodChannel _channel = const MethodChannel('vcrtc_plugin');

  static Future<String> get platformVersion async {
    return await _channel.invokeMethod('getPlatformVersion');
  }

  static login({String account, String password}) {
    return _channel
        .invokeMethod('login', {"account": account, "password": password});
  }

  static setServerAddress({String serverAddress, String httpsPort}) {
    return _channel.invokeMethod('setServerAddress',
        {"serverAddress": serverAddress, "httpsPort": httpsPort});
  }

  static Widget startMeeting(dynamic creationParams, Function(int) created) {
    if (Platform.isAndroid) {
      return AndroidView(
        viewType: "vcrtc_meeting_view",
        creationParams: creationParams,
        onPlatformViewCreated: created,
      );
    } else if (Platform.isIOS) {
      return UiKitView(
        viewType: "vcrtc_meeting_view",
        creationParams: creationParams,
        onPlatformViewCreated: created,
      );
    } else {
      return Text('不支持的平台');
    }
  }
}
