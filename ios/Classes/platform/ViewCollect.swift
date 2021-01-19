//
//  ViewCollect.swift
//  vcrtc_plugin
//
//  Created by apple on 2021/1/19.
//
import VCRTC.VCVideoView
import Foundation
class ViewCollect {
    static let sharedInstance = ViewCollect()
    private init(){}
    private var viewMap = [String:VCVideoView]()
    public func setView(viewId:String,view:VCVideoView){
        viewMap[viewId] = view
    }
    public func getView(viewId:String)->VCVideoView{
        return viewMap[viewId]!
    }
    public func removeView(viewId:String){
        viewMap.remove(at: viewMap.index(forKey: viewId)!)
    }
}
