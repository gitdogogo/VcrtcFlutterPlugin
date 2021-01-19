import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class VcrtcPlugin extends VctrcListener {
  static const MethodChannel _channel = const MethodChannel('vcrtc_plugin');

  VcrtcPlugin() : super(_channel);

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

  static Widget startMeeting(
      {Map<String, dynamic> creationParams,
      MessageCodec<dynamic> creationParamsCodec,
      Function() onTap,
      Function(int) created,
      Key key}) {
    if (Platform.isAndroid) {
      return AndroidView(
        key: key,
        viewType: "vcrtvChannelMeetingView",
        creationParams: creationParams,
        creationParamsCodec: StandardMessageCodec(),
        onPlatformViewCreated: created,
      );
    } else if (Platform.isIOS) {
      return UiKitView(
        key: key,
        viewType: "vcrtvChannelMeetingView",
        creationParams: creationParams,
        creationParamsCodec: StandardMessageCodec(),
        onPlatformViewCreated: created,
      );
    } else {
      return Text('不支持的平台');
    }
  }

  static connect() {
    return _channel.invokeMethod('connect');
  }

  static disconnect() {
    return _channel.invokeMethod('disconnect');
  }

  static void startRemoteView(int viewId) {
    MethodChannel _channel = MethodChannel('vcrtvChannelMeetingView$viewId');
    _channel.invokeMethod("startRemoteView");
  }

  static void startLocalView(int viewId) {
    MethodChannel _channel = MethodChannel('vcrtvChannelMeetingView$viewId');
    _channel.invokeMethod("startLocalView");
  }

  static void setStream(int viewId, dynamic params) {
    MethodChannel _channel = MethodChannel('vcrtvChannelMeetingView$viewId');
    _channel.invokeMethod("setStream", params);
  }
}

class VctrcListener {
  Set<ListenerValue> listeners = Set();
  VctrcListener(MethodChannel channel) {
    channel.setMethodCallHandler((methodCall) async {
      Map<String, dynamic> arguments = jsonDecode(methodCall.arguments);

      switch (methodCall.method) {
        case 'onListener':
          String typeStr = arguments['type'];
          var params = arguments['params'];

          VctrcListenerEnum type;

          for (var item in VctrcListenerEnum.values) {
            if (item.toString().replaceFirst("VctrcListenerEnum.", "") ==
                typeStr) {
              type = item;
              break;
            }
          }
          if (type == null) {
            throw MissingPluginException();
          }
          for (var item in listeners) {
            item(type, params);
          }
          break;
        default:
          throw MissingPluginException();
      }
    });
  }

  void addListener(ListenerValue func) {
    listeners.add(func);
  }

  void removeListener(ListenerValue func) {
    listeners.remove(func);
  }
}

/// @nodoc
typedef ListenerValue<P> = void Function(VctrcListenerEnum type, P params);

enum VctrcListenerEnum {
  onAuthorizationStatus,
  onCameraStream,
  onConnected,
  onCallConnected,
  onCallReconnected,
  onLocalVideo,
  onRemoteVideo,
  onAddView,
  onRemoveView,
  onLocalStream,
  onRemoteStream,
  onParticipantList,
  onAddParticipant,
  onRemoveParticipant,
  onUpdateParticipant,
  onStageVoice,
  onSubtitle,
  onChatMessage,
  onDisconnect,
  onCallDisconnect,
  onMediaDisconnect,
  onPresentation,
  onPresentationReload,
  onPresentaionShareState,
  onScreenShareState,
  onLayoutUpdate,
  onLayoutUpdateParticipants,
  onEnableOverlay,
  onRecordState,
  onLiveState,
  onRoleUpdate,
  onConferenceUpdate,
  onWhiteboardStart,
  onWhiteboardStop,
  onWhiteboardImageUpdate,
  onWhiteboardAddPayload,
  onWhiteboardDeletePayload,
  onWhiteboardClearPayload,
  onDecline,
  onUnmutePls,
  onError
}
