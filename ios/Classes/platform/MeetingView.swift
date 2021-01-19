//
//  Meeting.swift
//  Pods
//
//  Created by apple on 2021/1/18.
//

import Foundation
import Flutter
import VCRTC.VCVideoView
class MeetingView:NSObject,FlutterPlatformView{
    let _view:UIView
    init(_ frame: CGRect,viewID: Int64,args :Any?,messenger :FlutterBinaryMessenger) {
        _view = ViewCollect.sharedInstance.getView(viewId: (args as! Dictionary<String,String>)["uuid"]!)
        super.init()
    }
    public func view() -> UIView {
        return _view
    }
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult){
        
    }
}
