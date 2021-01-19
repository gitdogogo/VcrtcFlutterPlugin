//
//  MeetingDelegate.swift
//  vcrtc_plugin
//
//  Created by apple on 2021/1/18.
//
import VCRTC
import Foundation
class MeetingDelegate:NSObject,VCRtcModuleDelegate{
    let factory:MeetingViewFacory
    let channel:FlutterMethodChannel
    let invokeName = "onListener"
    init(_ meetingViewFactory:MeetingViewFacory,_ channel:FlutterMethodChannel) {
        factory = meetingViewFactory
        self.channel = channel
    }
    public func invokeListener(type: String, params: Any?) {
        var resultParams: [String: Any] = [:];
        resultParams["type"] = type;
        if let p = params {            resultParams["params"] = p;
        }
        channel.invokeMethod("onListener", arguments: JsonUtil.toJson(resultParams));
    }
    func vcRtc(_ module: VCRtcModule, didAddLocalView view: VCVideoView) {

        ViewCollect.sharedInstance.setView(viewId: "localView", view:view)
        invokeListener(type: "onLocalVideo", params: ["uuid":"localView"])
    }
    func vcRtc(_ module: VCRtcModule, didAdd view: VCVideoView, uuid: String) {
        ViewCollect.sharedInstance.setView(viewId: uuid, view:view)
        invokeListener(type: "onRemoteStream", params: ["uuid":uuid])
    }
    func vcRtc(_ module: VCRtcModule, didRemove view: VCVideoView, uuid: String) {
        ViewCollect.sharedInstance.removeView(viewId: uuid)
        invokeListener(type: "onRemoveView", params: ["uuid":uuid])
    }
}
