//
//  MeetingViewFacory.swift
//  Flutter
//
//  Created by apple on 2021/1/18.
//

import Foundation
import Flutter
class MeetingViewFacory:NSObject,FlutterPlatformViewFactory{
    static var SIGN:String = "vcrtvChannelMeetingView"
    var messenger:FlutterBinaryMessenger
        
    init(messenger:FlutterBinaryMessenger) {
        self.messenger = messenger
        super.init()
    }
    
    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        let view =  MeetingView(frame,viewID: viewId,args: args,messenger: messenger)
        FlutterMethodChannel(name: "\(MeetingViewFacory.SIGN)\(viewId)", binaryMessenger: self.messenger).setMethodCallHandler(view.handle)
        return view
    }
    
    func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
        return FlutterStandardMessageCodec.sharedInstance()
    }
}
